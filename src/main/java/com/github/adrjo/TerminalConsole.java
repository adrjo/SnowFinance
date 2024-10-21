package com.github.adrjo;

import com.github.adrjo.commands.Command;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TerminalConsole extends Thread implements Renderer {

    private boolean running = true;

    @Override
    public void startRenderer() {
        this.start();
    }

    @Override
    public void stopRenderer() {
        this.close();
    }

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
        List<Command> commands = SnowFinance.instance.getCommandManager().commands;

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
