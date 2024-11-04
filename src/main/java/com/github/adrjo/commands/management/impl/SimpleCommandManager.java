package com.github.adrjo.commands.management.impl;

import com.github.adrjo.commands.impl.*;
import com.github.adrjo.commands.management.CommandManager;

@Deprecated
public class SimpleCommandManager implements CommandManager {
    @Override
    public void registerCommands() {
        registerCommand(new AddTransactionCommand());
        registerCommand(new BalanceCommand());
        registerCommand(new ImportCommand());
        registerCommand(new ListTransactionCommand());
        registerCommand(new RemoveTransactionCommand());
        registerCommand(new FindCommand());
        registerCommand(new HelpCommand());
        registerCommand(new SummaryCommand());
    }
}
