package com.github.adrjo.accounts;

import com.github.adrjo.database.Database;

public class DatabaseAccountManager implements AccountManager {

    private final Database db;

    public DatabaseAccountManager(Database db) {
        this.db = db;
    }

    @Override
    public void addAccount(Account account) {

    }

    @Override
    public void removeAccount(int id) {

    }

    @Override
    public Account getAccount(int id) {
        return null;
    }
}
