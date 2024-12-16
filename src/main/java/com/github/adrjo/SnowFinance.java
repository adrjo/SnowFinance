package com.github.adrjo;

import com.github.adrjo.accounts.AccountManager;
import com.github.adrjo.accounts.DatabaseAccountManager;
import com.github.adrjo.commands.manager.CommandManager;
import com.github.adrjo.control.gui.GuiController;
import com.github.adrjo.control.Controller;
import com.github.adrjo.control.TerminalController;
import com.github.adrjo.database.Database;
import com.github.adrjo.database.DatabaseUtil;
import com.github.adrjo.transactions.impl.DatabaseTransactionManager;
import com.github.adrjo.transactions.TransactionManager;
import com.github.adrjo.users.DatabaseUserManager;
import com.github.adrjo.users.UserManager;

import java.util.Locale;

public class SnowFinance {
    public static SnowFinance instance = null;

    private TransactionManager transactionManager;
    private UserManager userManager;
    private AccountManager accountManager;
    private Controller controller;

    private Database database;

    private boolean useGui = false;
    public SnowFinance(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("--gui")) {
            System.out.println("Using JavaFX GUI.");
            useGui = true;
        } else {
            System.out.println("Using terminal UI. To change, add \"--gui\" command line argument!");
        }

        instance = this;
        // Fix commas being used in floats on some systems
        Locale.setDefault(Locale.US);

        System.out.println("Starting SnowFinance...");
        long now = System.currentTimeMillis();
        init();
        System.out.printf("Started in %dms\n", System.currentTimeMillis() - now);

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));


        DatabaseUtil.applySchema(database.getConnection());
        controller.startController();
    }

    private void init() {
        database = new Database();

        userManager = new DatabaseUserManager(database);
        accountManager = new DatabaseAccountManager(database);
        transactionManager = new DatabaseTransactionManager(database);
        transactionManager.load();

        if (useGui) {
            controller = new GuiController();
        } else {
            controller = new TerminalController();
        }
    }

    /**
     * Gets called on program shutdown, even on crash, so no data is lost
     */
    public void shutdown() {
        System.out.println("Shutting down");
        transactionManager.save();
        controller.stopController();
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public Controller getController() {
        return controller;
    }

    public CommandManager getCommandManager() {
        return controller.getCommandMenu().getCommandManager();
    }

    public Database getDatabase() {
        return database;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
