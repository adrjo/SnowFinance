package com.github.adrjo;

import com.github.adrjo.commands.CommandManager;
import com.github.adrjo.transactions.TransactionManager;

import java.util.Locale;

public class SnowFinance {
    public static SnowFinance instance = null;

    private TransactionManager transactionManager;
    private CommandManager commandManager;
    private TerminalConsole console;
    public SnowFinance() {
        instance = this;
        // Fix commas being used in floats on some systems
        Locale.setDefault(Locale.US);

        System.out.println("Starting SnowFinance...");
        long now = System.currentTimeMillis();
        init();
        System.out.printf("Started in %dms\n", System.currentTimeMillis() - now);

        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));

        console.start();
    }

    private void init() {
        transactionManager = new TransactionManager();
        commandManager = new CommandManager();
        console = new TerminalConsole();
    }

    private void shutdown() {
        System.out.println("Shutting down");
        transactionManager.close();
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
