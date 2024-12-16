package com.github.adrjo.users;

import com.github.adrjo.accounts.Account;

public interface UserManager {
    User getLoggedInUser();
    void setLoggedInUser(User user);

    void addUser(User user);
    void removeUser(int id);
    User getUser(int id);
    User getUser(String username);
    boolean userExists(String username);

    Account getAccountsForUser(int id);

    default void removeUser(User user) {
        this.removeUser(user.getId());
    }

    boolean login(String username, String password);
}
