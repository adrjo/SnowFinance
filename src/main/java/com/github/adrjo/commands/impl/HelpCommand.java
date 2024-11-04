package com.github.adrjo.commands.impl;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.RegisterCommand;

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
