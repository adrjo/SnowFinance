package com.github.adrjo.users;

import com.github.adrjo.accounts.Account;

public interface UserManager {

    void addUser(User user);
    void removeUser(int id);
    User getUser(int id);

    Account getAccountsForUser(int id);

    default void removeUser(User user) {
        this.removeUser(user.getId());
    }
}
