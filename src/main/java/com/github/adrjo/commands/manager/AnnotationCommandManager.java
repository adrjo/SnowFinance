package com.github.adrjo.commands.manager;

import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.menus.CommandMenu;
import com.github.adrjo.util.Helper;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.RegisterCommand;

import java.lang.reflect.Constructor;
import java.util.List;

public class AnnotationCommandManager implements CommandManager {
    @Override
    public void registerCommandsFor(Class<? extends CommandMenu> forClazz) {
        try {
            List<Class<?>> clazzes = Helper.findClasses("com.github.adrjo.commands.impl");

            clazzes.stream()
                    .filter(clazz -> {
                        for (ImplementsMenu anno : clazz.getAnnotationsByType(ImplementsMenu.class)) {
                            if (anno.value().equals(forClazz)) {
                                return true;
                            }
                        }
                        return false;
                    })
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
