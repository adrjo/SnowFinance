package com.github.adrjo.commands.impl;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.MainCommandMenu;
import com.github.adrjo.transactions.TransactionManager;

@ImplementsMenu(MainCommandMenu.class)
@RegisterCommand(
        name = "remove",
        description = "Removes a transaction by [ID]",
        requiredArgs = 1
)
public class RemoveTransactionCommand extends Command {

    @Deprecated
    public RemoveTransactionCommand() {
        super("remove", "Removes a transaction by [ID]", 1);
    }

    public RemoveTransactionCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

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
