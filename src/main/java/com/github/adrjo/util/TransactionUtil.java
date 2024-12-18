package com.github.adrjo.util;

import com.github.adrjo.transactions.Transaction;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;

public class TransactionUtil {
    public static void printTransactionInfo(int id, Transaction transaction) {
        System.out.printf("%d | %s | %.2f | %s\n",
                id,
                transaction.description(),
                transaction.amount(),
                Helper.DATE_FORMAT.format(new Date(transaction.timestamp()))
        );
    }

    public static void printStats(Map<Integer, Transaction> transactionsFound) {
        final double balance = getBalanceFromTransactions(transactionsFound);
        final int amtTransactions = transactionsFound.size();

        System.out.printf("""
                        %d transactions with a total outcome of %.1f SEK
                        Income: %.1f SEK
                        Expenses: %.1f SEK
                        Average income/expense: %.1f SEK
                        """,
                amtTransactions,
                balance,
                getIncomeForTransactions(transactionsFound),
                getExpensesForTransactions(transactionsFound),
                balance / amtTransactions);
    }

    public static double getBalanceFromTransactions(Map<Integer, Transaction> transactions) {
        return transactions.values().stream()
                .mapToDouble(Transaction::amount)
                .sum();
    }

    public static double getExpensesForTransactions(Map<Integer, Transaction> transactions) {
        return transactions.values().stream()
                .mapToDouble(Transaction::amount)
                .filter(amt -> amt < 0)
                .sum();
    }

    public static double getIncomeForTransactions(Map<Integer, Transaction> transactions) {
        return transactions.values().stream()
                .mapToDouble(Transaction::amount)
                .filter(amt -> amt > 0)
                .sum();
    }

    public static long epochMilli(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static void printTransactionInfoFor(Map<Integer, Transaction> transactions) {
        transactions.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .forEach(e -> TransactionUtil.printTransactionInfo(e.getKey(), e.getValue()));
    }
}
