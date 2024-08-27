package com.github.adrjo.commands.impl;

import com.github.adrjo.commands.Command;

public class BalanceCommand extends Command {

    public BalanceCommand() {
        super("bal", "returns your current balance");
    }

    @Override
    public void exec(String[] args) {
        //TODO:
        System.out.println("bal: 0 SEK");
    }
}
