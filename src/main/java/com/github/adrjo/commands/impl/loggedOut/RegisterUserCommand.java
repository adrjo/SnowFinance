package com.github.adrjo.commands.impl.loggedOut;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.LoggedOutCommandMenu;
import com.github.adrjo.commands.menus.MainCommandMenu;

@ImplementsMenu(LoggedOutCommandMenu.class)
@RegisterCommand(
        name = "register",
        description = "/register <user> <password>",
        requiredArgs = 2
)
public class RegisterUserCommand extends Command {
    public RegisterUserCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        //register logic

        //eventually...
        SnowFinance.instance.getController().setCommandMenu(new MainCommandMenu());
    }
}
