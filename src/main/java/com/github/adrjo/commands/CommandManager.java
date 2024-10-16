package com.github.adrjo.commands;

import com.github.adrjo.Helper;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    public List<Command> commands = new ArrayList<>();

    public CommandManager() {
        try {
            loadCommands();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCommands() throws Exception {
        List<Command> clazzCommands = Helper.findClasses("com.github.adrjo.commands.impl").stream()
                .filter(clazz -> clazz.isAnnotationPresent(RegisterCommand.class))
                .filter(clazz -> clazz.getSuperclass().equals(Command.class))
                .map(clazz -> {
                    try {
                        RegisterCommand annotation = clazz.getAnnotation(RegisterCommand.class);
                        Command command = (Command) clazz.getDeclaredConstructors()[0].newInstance();
                        command.setName(annotation.name());
                        command.setDescription(annotation.description());
                        return command;
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to load commands with reflection");
                    }
                })
                .toList();

        commands.addAll(clazzCommands);
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
