package com.github.adrjo.commands.management;


import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.menus.CommandMenu;

import java.util.ArrayList;
import java.util.List;

public interface CommandManager {
    List<Command> commands = new ArrayList<>();

    default List<Command> getCommands() {
        return commands;
    }

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

    void registerCommandsFor(Class<? extends CommandMenu> clazz);
}
