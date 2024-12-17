package com.github.adrjo.commands.impl.summary;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.AccountCommandMenu;
import com.github.adrjo.commands.menus.SummaryCommandMenu;
import com.github.adrjo.transactions.Transaction;
import com.github.adrjo.util.Helper;
import com.github.adrjo.util.TransactionUtil;

import java.time.LocalDate;
import java.util.Map;

@ImplementsMenu(SummaryCommandMenu.class)
@RegisterCommand(name = "1", description = "")
public class YearCommand extends Command {
    public YearCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        Map<Integer, Transaction> transactions = year();

        TransactionUtil.printTransactionInfoFor(transactions);

        System.out.println();
        TransactionUtil.printStats(transactions);
        SnowFinance.instance.getController().setCommandMenu(new AccountCommandMenu());
    }

    private Map<Integer, Transaction> year() {
        int year = Helper.getInput("Year");
        LocalDate date = LocalDate.of(year, 1, 1);

        return SnowFinance.instance.getTransactionManager().getTransactionsBetween(
                TransactionUtil.epochMilli(date),
                TransactionUtil.epochMilli(date.plusYears(1)));
    }
}
