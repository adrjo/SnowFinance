package com.github.adrjo.commands;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.accounts.Account;
import com.github.adrjo.accounts.AccountManager;
import com.github.adrjo.commands.menus.LoggedOutCommandMenu;
import com.github.adrjo.users.User;

import java.util.List;
import java.util.Scanner;

public abstract class Command {

    private final String name;
    private final String desc;
    private final int requiredArgs;

    public Command(String name, String desc, int requiredArgs) {
        this.name = name;
        this.desc = desc;
        this.requiredArgs = requiredArgs;
    }

    public Command(String name, String desc) {
        this(name, desc, 0);
    }

    public void exec(String[] args) {
        if (args.length < requiredArgs) {
            throw new IllegalArgumentException("Not enough args, needed: " + requiredArgs + "\n"
                    + this.getName() + ": " + this.getDesc());
        }
    }


    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    protected User validateLoggedIn() {
        User loggedInUser = SnowFinance.instance.getUserManager().getLoggedInUser();
        if (loggedInUser == null) {
            SnowFinance.instance.getController().setCommandMenu(new LoggedOutCommandMenu());
            throw new IllegalArgumentException("You're not logged in");
        }
        return loggedInUser;
    }

    protected List<Account> validateUserAccounts(User loggedInUser, boolean onlyOwned) {
        final AccountManager accManager = SnowFinance.instance.getAccountManager();

        List<Account> accounts = accManager.getAccountsForUser(loggedInUser.getId()).stream()
                .filter(account -> !onlyOwned || account.isOwner())
                .toList();

        if (accounts.isEmpty()) {
            throw new IllegalArgumentException("You do not have any accounts. Create one via 'create [Name] [Description]'");
        }
        return accounts;
    }

    protected int getIntegerInput() {
        final Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        return Integer.parseInt(scanner.nextLine());
    }
}
