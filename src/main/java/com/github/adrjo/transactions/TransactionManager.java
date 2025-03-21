package com.github.adrjo.transactions;

import java.util.Map;

public interface TransactionManager {

    void load();
    void save();

    boolean add(Transaction transaction);
    boolean add(int id, Transaction transaction);

    boolean remove(int id);

    Transaction get(int id);

    double getBalanceAt(long timestamp);
    Map<Integer, Transaction> getAllTransactions();
    Map<Integer, Transaction> getTransactionsBefore(long timestamp);
    Map<Integer, Transaction> getTransactionsBetween(long from, long until);
    Map<Integer, Transaction> find(String toSearch);

    // Helper functions that only forwards data
    default void addNow(String name, double amt) {
        this.add(new Transaction(name, amt, System.currentTimeMillis()));
    }

    default void add(String name, double amt, long timestamp) {
        this.add(new Transaction(name, amt, timestamp));
    }

    default double getBalance() {
        return getBalanceAt(System.currentTimeMillis());
    }

    default Map<Integer, Transaction> getTransactions() {
        return getTransactionsBefore(System.currentTimeMillis());
    }

}
