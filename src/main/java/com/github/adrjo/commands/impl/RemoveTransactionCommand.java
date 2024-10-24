package com.github.adrjo.commands.impl;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.RegisterCommand;
import com.github.adrjo.transactions.management.TransactionManager;

@RegisterCommand(
        name = "remove",
        description = "Removes a transaction by [ID]",
        requiredArgs = 1
)
public class RemoveTransactionCommand extends Command {

    @Override
    public void exec(String[] args) {
        super.exec(args);

        final int id = Integer.parseInt(args[0]);
        TransactionManager manager = SnowFinance.instance.getTransactionManager();
        if (manager.get(id) == null) {
            System.err.println(this.getName() + ": no such id");
            return;
        }
        manager.remove(id);
        System.out.println(this.getName() + ": removed transaction #" + id);
    }
}
