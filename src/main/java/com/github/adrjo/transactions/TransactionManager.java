package com.github.adrjo.transactions;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionManager {
    private final Map<Integer, Transaction> idtoTransactionMap = new HashMap<>();

    public TransactionManager() {
        //TODO: load data from file
    }

    public void add(Transaction transaction) {
        this.idtoTransactionMap.put(transaction.hashCode(), transaction);
    }

    public void addNow(String name, float amt) {
        this.add(new Transaction(name, amt, System.currentTimeMillis()));
    }

    public void add(String name, float amt, long timestamp) {
        this.add(new Transaction(name, amt, timestamp));
    }

    public double getBalanceAt(long timestamp) {
        return idtoTransactionMap.values().stream()
                .filter(transaction -> transaction.timestamp() <= timestamp)
                .mapToDouble(Transaction::amt)
                .sum();
    }

    public double getBalance() {
        return getBalanceAt(System.currentTimeMillis());
    }

    public Map<Integer, Transaction> getTransactions() {
        return getTransactionsBefore(System.currentTimeMillis());
    }

    public Map<Integer, Transaction> getTransactionsBefore(long timestamp) {
        return this.idtoTransactionMap.entrySet().stream()
                .filter((entry) -> entry.getValue().timestamp() < timestamp)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void remove(int id) {
        idtoTransactionMap.remove(id);
    }

    public Transaction get(int id) {
        return idtoTransactionMap.get(id);
    }

    public void close() {
//        for (Transaction transaction : transactions) {
//            //TODO: save
//        }
    }
}
