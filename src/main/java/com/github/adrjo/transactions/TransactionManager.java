package com.github.adrjo.transactions;

import com.github.adrjo.Helper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionManager {
    private final Map<Integer, Transaction> idtoTransactionMap = new HashMap<>();
    private int index = 0;

    public TransactionManager() {
        try {
            loadTransactions();
        } catch (IOException e) {
            System.err.println("Failed to load transactions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void add(Transaction transaction) {
        this.idtoTransactionMap.put(index++, transaction);
    }

    public void add(int id, Transaction transaction) {
        this.idtoTransactionMap.put(id, transaction);
        if (index <= id) {
            index = id + 1;
        }
    }

    public void addNow(String name, double amt) {
        this.add(new Transaction(name, amt, System.currentTimeMillis()));
    }

    public void add(String name, double amt, long timestamp) {
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

    public Map<Integer, Transaction> getAllTransactions() {
        return idtoTransactionMap;
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

    //TODO: save to sql db
    public void close() {
        try (FileWriter writer = new FileWriter(Helper.DATABASE, StandardCharsets.UTF_8)) {
            for (Map.Entry<Integer, Transaction> entry : idtoTransactionMap.entrySet()) {
                final int id = entry.getKey();
                final Transaction transaction = entry.getValue();
                writer.write(String.format("%d,%s,%.3f,%d\n", id, transaction.desc(), transaction.amt(), transaction.timestamp()));
            }
        } catch (IOException e) {
            System.err.println("Error in saving transactions to file: " + e.getMessage());
        }
    }

    private void loadTransactions() throws IOException {
        Path path = Path.of(Helper.DATABASE);
        if (!path.toFile().exists()) return;

        Files.readAllLines(path, StandardCharsets.UTF_8).forEach(line -> {
            String[] split = line.split(",");
            int id = Integer.parseInt(split[0]);
            Transaction transaction = new Transaction(split[1], Double.parseDouble(split[2]), Long.parseLong(split[3]));
            this.add(id, transaction);
        });
    }
}
