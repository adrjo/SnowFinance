package com.github.adrjo.commands.management.impl;

import com.github.adrjo.util.Helper;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.RegisterCommand;
import com.github.adrjo.commands.management.CommandManager;

import java.lang.reflect.Constructor;

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
                            Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, String.class, int.class);
                            Command command = (Command) constructor.newInstance(
                                    annotation.name(),
                                    annotation.description(),
                                    annotation.requiredArgs()
                            );

                            this.registerCommand(command);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException("Failed to load command with reflection: " + e.getMessage(), e);
                        }
                    });
        } catch (Exception e) {
            System.err.println("Failed to find commands to register: " + e.getMessage());
        }
    }
}
