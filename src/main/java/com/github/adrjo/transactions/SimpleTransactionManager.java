package com.github.adrjo.transactions;

import com.github.adrjo.Helper;
import com.github.adrjo.fileloading.SnowFileLoader;
import com.github.adrjo.fileloading.TransactionFileLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SimpleTransactionManager implements TransactionManager {
    private final Map<Integer, Transaction> idtoTransactionMap = new HashMap<>();
    private int index = 0;

    @Override
    public void load() {
        try {
            TransactionFileLoader loader = new SnowFileLoader();
            int count = loader.load(new File(Helper.DATABASE));

            System.out.println("Loaded " + count + " transactions from file");
        } catch (IOException e) {
            System.err.println("Failed to load transactions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try (FileWriter writer = new FileWriter(Helper.DATABASE, StandardCharsets.UTF_8)) {
            for (Map.Entry<Integer, Transaction> entry : idtoTransactionMap.entrySet()) {
                final int id = entry.getKey();
                final Transaction transaction = entry.getValue();
                writer.write(String.format("%d,\"%s\",%.3f,%d\n", id, transaction.desc(), transaction.amt(), transaction.timestamp()));
            }
        } catch (IOException e) {
            System.err.println("Error in saving transactions to file: " + e.getMessage());
        }
    }

    @Override
    public void add(Transaction transaction) {
        this.add(index, transaction);
    }

    @Override
    public void add(int id, Transaction transaction) {
        if (index <= id) {
            index = id + 1;
        }
        this.idtoTransactionMap.put(id, transaction);
    }

    @Override
    public double getBalanceAt(long timestamp) {
        return idtoTransactionMap.values().stream()
                .filter(transaction -> transaction.timestamp() <= timestamp)
                .mapToDouble(Transaction::amt)
                .sum();
    }

    @Override
    public Map<Integer, Transaction> getAllTransactions() {
        return idtoTransactionMap;
    }


    @Override
    public Map<Integer, Transaction> getTransactionsBefore(long timestamp) {
        return this.idtoTransactionMap.entrySet().stream()
                .filter((entry) -> entry.getValue().timestamp() < timestamp)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<Integer, Transaction> getTransactionsBetween(long from, long until) {
        return this.idtoTransactionMap.entrySet().stream()
                .filter((entry) -> entry.getValue().timestamp() >= from)
                .filter((entry) -> entry.getValue().timestamp() < until)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void remove(int id) {
        idtoTransactionMap.remove(id);
    }

    @Override
    public void remove(Transaction transaction) {
        idtoTransactionMap.values().removeIf(t -> t.equals(transaction));
    }

    @Override
    public Transaction get(int id) {
        return idtoTransactionMap.get(id);
    }
}
