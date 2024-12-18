package com.github.adrjo.accounts;

import com.github.adrjo.users.User;

import java.util.List;
import java.util.Set;

public interface AccountManager {

    Account getActiveAccount();
    void setActiveAccount(Account account);

    /** return true on success **/
    boolean addAccount(Account account);
    boolean removeAccount(int id);
    Account getAccount(int id);

    boolean addUserToAccount(int userId, int accountId);
    boolean removeUserFromAccount(int userId, int accountId);

    Set<User> getUsersForAccount(int id);

    default boolean removeAccount(Account account) {
        return this.removeAccount(account.getId());
    }

    void leave();

    List<Account> getAccountsForUser(int id);
}
