package com.github.adrjo.commands.management.impl;

import com.github.adrjo.commands.impl.*;
import com.github.adrjo.commands.management.CommandManager;

public class SimpleCommandManager implements CommandManager {
    @Override
    public void registerCommands() {
        registerCommand(new AddTransactionCommand());
        registerCommand(new BalanceCommand());
        registerCommand(new ImportCommand());
        registerCommand(new ListTransactionCommand());
        registerCommand(new RemoveTransactionCommand());
    }
}
