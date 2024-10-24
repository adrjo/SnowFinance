package com.github.adrjo.util;

import com.github.adrjo.transactions.Transaction;

import java.util.Date;
import java.util.Map;

public class TransactionUtil {
    public static void printTransactionInfo(int id, Transaction transaction) {
        System.out.printf("%d | %s | %.2f | %s\n",
                id,
                transaction.desc(),
                transaction.amt(),
                Helper.DATE_FORMAT.format(new Date(transaction.timestamp()))
        );
    }

    public static void printStats(Map<Integer, Transaction> transactionsFound) {
        System.out.printf("""
                        %d transactions with a total outcome of %.1f SEK
                        Income: %.1f SEK
                        Expenses: %.1f SEK
                        """,
                transactionsFound.size(),
                getBalanceFromTransactions(transactionsFound),
                getIncomeForTransactions(transactionsFound),
                getExpensesForTransactions(transactionsFound));
    }

    public static double getBalanceFromTransactions(Map<Integer, Transaction> transactions) {
        return transactions.values().stream()
                .mapToDouble(Transaction::amt)
                .sum();
    }

    public static double getExpensesForTransactions(Map<Integer, Transaction> transactions) {
        return transactions.values().stream()
                .mapToDouble(Transaction::amt)
                .filter(amt -> amt < 0)
                .sum();
    }

    public static double getIncomeForTransactions(Map<Integer, Transaction> transactions) {
        return transactions.values().stream()
                .mapToDouble(Transaction::amt)
                .filter(amt -> amt > 0)
                .sum();
    }
}
