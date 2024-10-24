package com.github.adrjo.commands.impl;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.RegisterCommand;

@RegisterCommand(
        name = "help",
        description = "Lists commands and their usage"
)
public class HelpCommand extends Command {

    @Override
    public void exec(String[] args) {
        for (Command command : SnowFinance.instance.getCommandManager().getCommands()) {
            System.out.println(command.getName() + " - " + command.getDesc());
        }
    }
}
