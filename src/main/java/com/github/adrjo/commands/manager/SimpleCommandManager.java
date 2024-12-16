package com.github.adrjo.commands.manager;

import com.github.adrjo.commands.impl.*;
import com.github.adrjo.commands.impl.loggedIn.*;
import com.github.adrjo.commands.menus.CommandMenu;

@Deprecated
public class SimpleCommandManager implements CommandManager {

    @Override
    public void registerCommandsFor(Class<? extends CommandMenu> clazz) {
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
