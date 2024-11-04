package com.github.adrjo.commands.menus;

import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.management.CommandManager;
import com.github.adrjo.commands.management.impl.AnnotationCommandManager;

import java.util.List;

public abstract class CommandMenu {
    protected final CommandManager commandManager = new AnnotationCommandManager();

    public CommandMenu() {
        commandManager.getCommands().clear();
        commandManager.registerCommandsFor(this.getClass());
    }

   public void runCommand(String commandString, String[] args) {
       final Command command = commandManager.get(commandString);
       if (command == null) {
           System.err.printf("%s: command not found\n", commandString);
           return;
       }

       if (contains(args, "-?", "?")) {
           System.err.printf("%s: %s\n", command.getName(), command.getDesc());
           return;
       }
       try {
           command.exec(args);
       } catch (Exception e) {
           System.err.println(e.getMessage());
       }
   }

    private boolean contains(String[] args, String... strings) {
        for (String arg : args) {
            for (String s : strings) {
                if (arg.equalsIgnoreCase(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public String getCommandList() {
        StringBuilder commandList = new StringBuilder();
        List<Command> commands = commandManager.commands;

        for (int i = 0; i < commands.size(); ++i) {
            Command command = commands.get(i);
            commandList.append(command.getName());
            if (i != commands.size() - 1) {
                commandList.append(", ");
            }
        }

        return commandList.toString();
    }
}
