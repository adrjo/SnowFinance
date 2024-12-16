package com.github.adrjo.accounts;

public interface AccountManager {

    void addAccount(Account account);
    void removeAccount(int id);
    Account getAccount(int id);

    default void removeAccount(Account account) {
        this.removeAccount(account.getId());
    }
}
