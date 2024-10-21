package com.github.adrjo.commands;


import java.util.ArrayList;
import java.util.List;

public interface CommandManager {
    List<Command> commands = new ArrayList<>();

    void registerCommands();

    default void registerCommand(Command command) {
        commands.add(command);
    }

    default Command get(String command) {
        for (Command c : commands) {
            if (c.getName().equalsIgnoreCase(command)) {
                return c;
            }
        }
        return null;
    }
}
