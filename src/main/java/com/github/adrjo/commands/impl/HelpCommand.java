package com.github.adrjo.commands.impl;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.LoggedOutCommandMenu;
import com.github.adrjo.commands.menus.AccountCommandMenu;
import com.github.adrjo.commands.menus.MainCommandMenu;
import com.github.adrjo.commands.menus.SummaryCommandMenu;

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
        for (Command command : SnowFinance.instance.getCommandManager().getCommands()) {
            System.out.println(command.getName() + " - " + command.getDesc());
        }
    }
}
