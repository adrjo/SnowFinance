package com.github.adrjo.commands.impl.main;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.accounts.Account;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.MainCommandMenu;
import com.github.adrjo.util.Helper;

@ImplementsMenu(MainCommandMenu.class)
@RegisterCommand(
        name = "create",
        description = "Creates a new account (create [Name] [Description])",
        requiredArgs = 2
)
public class CreateAccountCommand extends Command {
    public CreateAccountCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        final String name = args[0];
        final String description = Helper.joinArgs(args, 1);

        if (SnowFinance.instance.getAccountManager().addAccount(new Account(name, description))) {
            System.out.println("Created " + name + "!");
        }
    }
}