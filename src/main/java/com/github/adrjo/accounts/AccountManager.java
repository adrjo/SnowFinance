package com.github.adrjo.accounts;

import com.github.adrjo.users.User;

import java.util.Set;

public interface AccountManager {

    /** return true on success **/
    boolean addAccount(Account account);
    boolean removeAccount(int id);
    Account getAccount(int id);

    boolean addUserToAccount(int userId, int accountId);
    void removeUserFromAccount(int userId, int accountId);

    Set<User> getUsersForAccount(int id);

    default boolean removeAccount(Account account) {
        return this.removeAccount(account.getId());
    }

    Set<Account> getAccountsForUser(int id);
}
