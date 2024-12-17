package com.github.adrjo.commands.impl.main;

import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.MainCommandMenu;

@ImplementsMenu(MainCommandMenu.class)
@RegisterCommand(
        name = "remove",
        description = "Removes one of your accounts"
)
public class RemoveAccountCommand extends Command {
    public RemoveAccountCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);
    }
}
