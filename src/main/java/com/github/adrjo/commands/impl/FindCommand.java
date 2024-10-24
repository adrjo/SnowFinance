package com.github.adrjo.commands.impl;

import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.RegisterCommand;

@RegisterCommand(
        name = "find",
        description = "Find transactions by name",
        requiredArgs = 1
)
public class FindCommand extends Command {
    @Override
    public void exec(String[] args) {

    }
}
