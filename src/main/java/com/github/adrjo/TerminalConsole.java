package com.github.adrjo;

import com.github.adrjo.commands.Command;

import java.util.Arrays;
import java.util.Scanner;

public class TerminalConsole extends Thread {

    @Override
    public void run() {
        System.out.println("Welcome to SnowFinance");
        String commandList = getCommandList();
        System.out.println("Commands: " + commandList);
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String line = scan.nextLine();
            String[] args = line.split(" ");
            Command command = SnowFinance.instance.getCommandManager().get(args[0]);
            if (command == null) {
                System.err.printf("%s: command not found\n", args[0]);
                continue;
            }

            String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
            command.exec(commandArgs);
        }
        System.out.println("Bye!");
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
