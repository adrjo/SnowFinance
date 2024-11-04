package com.github.adrjo.control.impl;

import com.github.adrjo.commands.menus.CommandMenu;
import com.github.adrjo.commands.menus.impl.MainCommandMenu;
import com.github.adrjo.control.Controller;

import java.util.Arrays;
import java.util.Scanner;

public class TerminalController implements Controller {

    private boolean running = true;
    private CommandMenu commandMenu;

    @Override
    public void startController() {
        this.run();
    }

    @Override
    public void stopController() {
        this.close();
    }

    public void run() {
        System.out.println("Welcome to SnowFinance");
        commandMenu = new MainCommandMenu();
        Scanner scan = new Scanner(System.in);

        while (this.running) {
            System.out.print("> ");
            final String line = scan.nextLine();
            String[] args = line.split(" ");
            
            final String commandString = args[0];
            // Shutdown
            if (commandString.equalsIgnoreCase("stop") || commandString.equalsIgnoreCase("exit")) {
                this.close();
            }
            String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);

            commandMenu.runCommand(commandString, commandArgs);
        }
        System.out.println("Bye!");
    }

    @Override
    public CommandMenu getCommandMenu() {
        return commandMenu;
    }

    @Override
    public void setCommandMenu(CommandMenu menu) {
        this.commandMenu = menu;
    }

    public void close() {
        running = false;
    }
}
