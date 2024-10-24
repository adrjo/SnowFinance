package com.github.adrjo.transactions.management.impl;

import com.github.adrjo.transactions.Transaction;
import com.github.adrjo.transactions.management.TransactionManager;

import java.util.Map;

public class DatabaseTransactionManager implements TransactionManager {

    @Override
    public void load() {
        throw new IllegalArgumentException("Not implemented");
        // load into memory
        // select id, name, amt, timestamp from transactions;
    }

    @Override
    public void save() {
        throw new IllegalArgumentException("Not implemented");
        // is this function needed? should save to database every add
        // save to database
        // insert into transactions(name,amt,timestamp) values("name", amt, timestamp)
    }

    @Override
    public void add(Transaction transaction) {
        throw new IllegalArgumentException("Not implemented");
        // insert into transactions(name,amt,timestamp) values("name", amt, timestamp)
        // id is autoincrement in db
    }

    @Override
    public void add(int id, Transaction transaction) {
        throw new IllegalArgumentException("Not implemented");
        // insert into transactions(id, name,amt,timestamp) values(id, "name", amt, timestamp)
    }

    @Override
    public void remove(Transaction transaction) {
        throw new IllegalArgumentException("Not implemented");
        // maybe not needed?
    }

    @Override
    public void remove(int id) {
        throw new IllegalArgumentException("Not implemented");
        // remove from transactions where id = id
    }

    @Override
    public Transaction get(int id) {
        throw new IllegalArgumentException("Not implemented");
        // select name, amt, timestamp from transactions where id = id
    }

    @Override
    public double getBalanceAt(long timestamp) {
        throw new IllegalArgumentException("Not implemented");
        // List<Double> amts = select amt from transactions where this.timestamp <= timestamp
        // int tot = 0
        // foreach amts tot += amt
    }

    @Override
    public Map<Integer, Transaction> getAllTransactions() {
        throw new IllegalArgumentException("Not implemented");
        // select id, name, amt, timestamp from transactions;
    }

    @Override
    public Map<Integer, Transaction> getTransactionsBefore(long timestamp) {
        throw new IllegalArgumentException("Not implemented");
        // select id, name, amt, timestamp from transactions where this.timestamp < timestamp;
    }

    @Override
    public Map<Integer, Transaction> getTransactionsBetween(long from, long until) {
        throw new IllegalArgumentException("Not implemented");
        // select id, name, amt, timestamp from transactions where this.timestamp > from and this.timestamp < until;
    }
}
