package com.github.adrjo.commands.impl.summary;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.impl.ImplementsMenu;
import com.github.adrjo.commands.annotations.impl.RegisterCommand;
import com.github.adrjo.commands.menus.impl.MainCommandMenu;
import com.github.adrjo.commands.menus.impl.SummaryCommandMenu;
import com.github.adrjo.transactions.Transaction;
import com.github.adrjo.util.TransactionUtil;

import java.time.LocalDate;
import java.util.Map;

import static com.github.adrjo.util.Helper.getInput;

@ImplementsMenu(SummaryCommandMenu.class)
@RegisterCommand(name = "2", description = "")
public class YearMonthCommand extends Command {
    public YearMonthCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        Map<Integer, Transaction> transactions = yearMonth();

        TransactionUtil.printTransactionInfoFor(transactions);

        System.out.println();
        TransactionUtil.printStats(transactions);
        SnowFinance.instance.getController().setCommandMenu(new MainCommandMenu());
    }

    private Map<Integer, Transaction> yearMonth() {
        int year = getInput("Year");
        int month = getInput("Month");

        LocalDate date = LocalDate.of(year, month, 1);

        return SnowFinance.instance.getTransactionManager().getTransactionsBetween(
                TransactionUtil.epochMilli(date),
                TransactionUtil.epochMilli(date.plusMonths(1)));
    }
}
