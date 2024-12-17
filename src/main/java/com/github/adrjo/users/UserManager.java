package com.github.adrjo.users;

import com.github.adrjo.accounts.Account;

import java.util.List;

public interface UserManager {
    User getLoggedInUser();
    void setLoggedInUser(User user);

    void addUser(User user);

    /**
     * Removes by user-id
     * @param id the user-id
     * @return true if successful
     */
    boolean removeUser(int id);
    User getUser(int id);
    User getUser(String username);
    boolean userExists(String username);

    Account getAccountsForUser(int id);

    default boolean removeUser(User user) {
        return this.removeUser(user.getId());
    }

    boolean login(String username, String password);
    void logout();

    List<User> getAllUsers();
}
