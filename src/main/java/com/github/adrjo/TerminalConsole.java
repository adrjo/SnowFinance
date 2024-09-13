package com.github.adrjo;

import com.github.adrjo.commands.Command;

import java.util.Arrays;
import java.util.Scanner;

public class TerminalConsole extends Thread {

    private boolean running = true;

    @Override
    public void run() {
        System.out.println("Welcome to SnowFinance");
        System.out.println("Commands: " + getCommandList());
        Scanner scan = new Scanner(System.in);
        while (this.running) {
            final String line = scan.nextLine();
            String[] args = line.split(" ");
            
            final String commandString = args[0];
            // Shutdown
            if (commandString.equalsIgnoreCase("stop") || commandString.equalsIgnoreCase("exit")) {
                System.exit(0);
            }
            
            final Command command = SnowFinance.instance.getCommandManager().get(commandString);
            if (command == null) {
                System.err.printf("%s: command not found\n", commandString);
                continue;
            }

            String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
            if (contains(args, "-?", "?", "help")) {
                System.err.printf("%s: %s\n", command.getName(), command.getDesc());
                continue;
            }
            command.exec(commandArgs);
        }
        System.out.println("Bye!");
    }

    public void close() {
        running = false;
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

    private String getCommandList() {
        StringBuilder commandList = new StringBuilder();
        for (Command command : SnowFinance.instance.getCommandManager().commands) {
            commandList.append(command.getName()).append(", ");
        }
        // Delete last comma
        commandList.delete(commandList.length() - 2, commandList.length());
        return commandList.toString();
    }
}
