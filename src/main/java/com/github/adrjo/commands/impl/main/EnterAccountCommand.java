package com.github.adrjo.commands.impl.main;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.AccountCommandMenu;
import com.github.adrjo.commands.menus.MainCommandMenu;

@ImplementsMenu(MainCommandMenu.class)
@RegisterCommand(
        name = "enter",
        description = "Enters an account",
        requiredArgs = 1
)
public class EnterAccountCommand extends Command {
    public EnterAccountCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        SnowFinance.instance.getController().setCommandMenu(new AccountCommandMenu());
    }
}