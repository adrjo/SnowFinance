package com.github.adrjo.commands.management.impl;

import com.github.adrjo.Helper;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.RegisterCommand;
import com.github.adrjo.commands.management.CommandManager;

public class AnnotationCommandManager implements CommandManager {
    @Override
    public void registerCommands() {
        try {
            Helper.findClasses("com.github.adrjo.commands.impl").stream()
                    .filter(clazz -> clazz.isAnnotationPresent(RegisterCommand.class))
                    .filter(clazz -> clazz.getSuperclass().equals(Command.class))
                    .forEach(clazz -> {
                        try {
                            RegisterCommand annotation = clazz.getAnnotation(RegisterCommand.class);
                            Command command = (Command) clazz.getDeclaredConstructors()[0].newInstance();
                            command.setName(annotation.name());
                            command.setDescription(annotation.description());
                            this.registerCommand(command);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to load command with reflection");
                        }
                    });
        } catch (Exception e) {
            System.err.println("Failed to find commands to register: " + e.getMessage());
        }
    }
}
