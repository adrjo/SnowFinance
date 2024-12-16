package com.github.adrjo.users;

import com.github.adrjo.accounts.Account;
import com.github.adrjo.database.Database;

public class DatabaseUserManager implements UserManager {
    private final Database db;

    public DatabaseUserManager(Database db) {
        this.db = db;
    }

    @Override
    public void addUser(User user) {
    }

    @Override
    public void removeUser(int id) {

    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public Account getAccountsForUser(int id) {
        return null;
    }
}
