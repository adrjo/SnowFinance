package com.github.adrjo.commands;

import com.github.adrjo.commands.impl.AddTransactionCommand;
import com.github.adrjo.commands.impl.BalanceCommand;
import com.github.adrjo.commands.impl.ListTransactionCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    public List<Command> commands = new ArrayList<>();

    public CommandManager() {
        //TODO: load commands automatically
        commands.add(new BalanceCommand());
        commands.add(new AddTransactionCommand());
        commands.add(new ListTransactionCommand());
    }

    public Command get(String command) {
        for (Command c : commands) {
            if (c.getName().equalsIgnoreCase(command)) {
                return c;
            }
        }
        return null;
    }
}
