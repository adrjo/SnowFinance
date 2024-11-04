package com.github.adrjo.commands.impl;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.RegisterCommand;
import com.github.adrjo.util.DateInput;
import com.github.adrjo.transactions.Transaction;
import com.github.adrjo.util.TransactionUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

@RegisterCommand(
        name = "summary",
        description = "View full transaction summary (balance, expenses, income) or optionally by year, month, day or week"
)
public class SummaryCommand extends Command {
    private Scanner scanner;

    @Deprecated
    public SummaryCommand() {
        super("summary", "View full transaction summary (balance, expenses, income) or optionally by year, month, day or week");
    }

    public SummaryCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {

        System.out.println("""
                            Welcome to reports!
                            Show reports by:
                            1. Year
                            2. Year and Month
                            3. Year, Month and Day
                            4. Year and Week
                            5. Full reports
                            """);

        this.scanner = new Scanner(System.in);
        int input = Integer.parseInt(scanner.nextLine().charAt(0)+"");

        Map<Integer, Transaction> transactions;
        switch (input) {
            case 1 -> transactions = year();
            case 2 -> transactions = yearMonth();
            case 3 -> transactions = yearMonthDay();
            case 4 -> transactions = yearWeek();
            default -> transactions = full();
        }

        transactions.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .forEach(e -> TransactionUtil.printTransactionInfo(e.getKey(), e.getValue()));

        System.out.println();
        TransactionUtil.printStats(transactions);
    }

    private Map<Integer, Transaction> full() {
        return SnowFinance.instance.getTransactionManager().getAllTransactions();
    }

    private Map<Integer, Transaction> year() {
        int year = getInput("Year");
        LocalDate date = LocalDate.of(year, 1, 1);

        return SnowFinance.instance.getTransactionManager().getTransactionsBetween(
                TransactionUtil.epochMilli(date),
                TransactionUtil.epochMilli(date.plusYears(1)));
    }

    private Map<Integer, Transaction> yearMonth() {
        int year = getInput("Year");
        int month = getInput("Month");

        LocalDate date = LocalDate.of(year, month, 1);

        return SnowFinance.instance.getTransactionManager().getTransactionsBetween(
                TransactionUtil.epochMilli(date),
                TransactionUtil.epochMilli(date.plusMonths(1)));
    }

    private Map<Integer, Transaction> yearMonthDay() {
        int year = getInput("Year");
        int month = getInput("Month");
        int day = getInput("Day");

        LocalDate date = LocalDate.of(year, month, day);

        return SnowFinance.instance.getTransactionManager().getTransactionsBetween(
                TransactionUtil.epochMilli(date),
                TransactionUtil.epochMilli(date.plusDays(1)));
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

    private int getInput(String lookingFor) {
        System.out.print(lookingFor + ": ");
        return Integer.parseInt(scanner.nextLine());
    }
}
