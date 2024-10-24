package com.github.adrjo;

import com.github.adrjo.commands.management.impl.AnnotationCommandManager;
import com.github.adrjo.commands.management.CommandManager;
import com.github.adrjo.rendering.impl.GuiRenderer;
import com.github.adrjo.rendering.Renderer;
import com.github.adrjo.rendering.impl.TerminalRenderer;
import com.github.adrjo.transactions.management.impl.SimpleTransactionManager;
import com.github.adrjo.transactions.management.TransactionManager;

import java.util.Locale;

public class SnowFinance {
    public static SnowFinance instance = null;

    private TransactionManager transactionManager;
    private CommandManager commandManager;
    private Renderer renderer;
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

        renderer.startRenderer();
    }

    private void init() {
        transactionManager = new SimpleTransactionManager();
        transactionManager.load();

        commandManager = new AnnotationCommandManager();
        commandManager.registerCommands();

        if (useGui) {
            renderer = new GuiRenderer();
        } else {
            renderer = new TerminalRenderer();
        }
    }

    /**
     * Gets called on program shutdown, even on crash, so no data is lost
     */
    public void shutdown() {
        System.out.println("Shutting down");
        transactionManager.save();
        renderer.stopRenderer();
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
