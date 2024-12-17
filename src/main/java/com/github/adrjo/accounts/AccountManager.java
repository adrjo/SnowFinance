package com.github.adrjo.accounts;

import com.github.adrjo.users.User;

import java.util.Set;

public interface AccountManager {

    /** return true on success **/
    boolean addAccount(Account account);
    void removeAccount(int id);
    Account getAccount(int id);

    void addUserToAccount(int userId, int accountId);
    void removeUserFromAccount(int userId, int accountId);

    Set<User> getUsersForAccount(int id);

    default void removeAccount(Account account) {
        this.removeAccount(account.getId());
    }

    Set<Account> getAccountsForUser(int id);
}
