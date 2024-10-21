package com.github.adrjo;

import com.github.adrjo.commands.AnnotationCommandManager;
import com.github.adrjo.commands.CommandManager;
import com.github.adrjo.gui.GuiRenderer;
import com.github.adrjo.transactions.TransactionManager;

import java.util.Locale;

public class SnowFinance {
    public static SnowFinance instance = null;

    private TransactionManager transactionManager;
    private CommandManager commandManager;
    private GuiRenderer guiRenderer;
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
        guiRenderer.start();
    }

    private void init() {
        transactionManager = new TransactionManager();
        transactionManager.load();

        commandManager = new AnnotationCommandManager();
        commandManager.registerCommands();

        console = new TerminalConsole();
        guiRenderer = new GuiRenderer();
    }

    public void shutdown() {
        System.out.println("Shutting down");
        transactionManager.close();
        console.close();
        try {
            guiRenderer.stop();
        } catch (Exception e) {}
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
