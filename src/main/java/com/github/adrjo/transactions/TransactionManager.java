package com.github.adrjo.transactions;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private final List<Transaction> transactions = new ArrayList<>();

    public TransactionManager() {
        //TODO: load data from file
    }

    public void add(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void addNow(String name, float amt) {
        this.add(new Transaction(name, amt, System.currentTimeMillis()));
    }

    public void add(String name, float amt, long timestamp) {
        this.add(new Transaction(name, amt, timestamp));
    }

    public void close() {
        for (Transaction transaction : transactions) {
            //TODO: save
        }
    }
}
