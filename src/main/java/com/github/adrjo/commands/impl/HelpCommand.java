package com.github.adrjo.commands.impl;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.LoggedOutCommandMenu;
import com.github.adrjo.commands.menus.AccountCommandMenu;
import com.github.adrjo.commands.menus.MainCommandMenu;
import com.github.adrjo.commands.menus.SummaryCommandMenu;
import com.github.adrjo.util.TextColor;

import java.util.Comparator;

@ImplementsMenu(LoggedOutCommandMenu.class)
@ImplementsMenu(AccountCommandMenu.class)
@ImplementsMenu(SummaryCommandMenu.class)
@ImplementsMenu(MainCommandMenu.class)
@RegisterCommand(
        name = "help",
        description = "Lists commands and their usage"
)
public class HelpCommand extends Command {

    @Deprecated
    public HelpCommand(){
        super("help", "Lists commands and their usage");
    }

    public HelpCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        int longest = SnowFinance.instance.getCommandManager().getCommands().stream()
                .map(Command::getName)
                .map(String::length)
                .max(Comparator.comparingInt(l -> l)).get();

        for (Command command : SnowFinance.instance.getCommandManager().getCommands()) {
            int spaces = longest - command.getName().length();
            String[] lines = command.getDesc().split("\n");

            if (lines.length > 1) {
                System.out.println(TextColor.GREEN + command.getName() + TextColor.RESET + " ".repeat(spaces) + " - " + lines[0]);
                for (int i = 1; i < lines.length; ++i) {
                    System.out.println(" ".repeat(longest + 3) + lines[1]);
                }
                continue;
            }
            System.out.println(TextColor.GREEN + command.getName() + TextColor.RESET + " ".repeat(spaces) + " - " + command.getDesc());
        }
    }
}
