package com.github.adrjo.commands.impl.account;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.AccountCommandMenu;
import com.github.adrjo.commands.menus.MainCommandMenu;

@ImplementsMenu(AccountCommandMenu.class)
@RegisterCommand(
        name = "back",
        description = "Leaves the account command menu"
)
public class BackCommand extends Command {
    public BackCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        System.out.println("Leaving account menu!");
        SnowFinance.instance.getAccountManager().leave();
        SnowFinance.instance.getController().setCommandMenu(new MainCommandMenu());
    }
}
