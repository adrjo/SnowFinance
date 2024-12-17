package com.github.adrjo.commands.impl.summary;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.AccountCommandMenu;
import com.github.adrjo.commands.menus.SummaryCommandMenu;
import com.github.adrjo.transactions.Transaction;
import com.github.adrjo.util.DateInput;
import com.github.adrjo.util.TransactionUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

import static com.github.adrjo.util.Helper.getInput;

@ImplementsMenu(SummaryCommandMenu.class)
@RegisterCommand(name = "4", description = "")
public class YearWeekCommand extends Command {
    public YearWeekCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        Map<Integer, Transaction> transactions = yearWeek();

        TransactionUtil.printTransactionInfoFor(transactions);

        System.out.println();
        TransactionUtil.printStats(transactions);
        SnowFinance.instance.getController().setCommandMenu(new AccountCommandMenu());
    }

    private Map<Integer, Transaction> yearWeek() {
        int year = getInput("Year");
        int week = getInput("Week");

        DateInput input = new DateInput(DateInput.DateType.WEEK, year, week);
        LocalDate date = input.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return SnowFinance.instance.getTransactionManager().getTransactionsBetween(
                TransactionUtil.epochMilli(date),
                TransactionUtil.epochMilli(date.plusWeeks(1)));
    }
}
