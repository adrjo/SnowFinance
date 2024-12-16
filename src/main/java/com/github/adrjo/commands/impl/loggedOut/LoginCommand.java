package com.github.adrjo.commands.impl.loggedOut;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.LoggedOutCommandMenu;
import com.github.adrjo.commands.menus.MainCommandMenu;

@ImplementsMenu(LoggedOutCommandMenu.class)
@RegisterCommand(
        name = "login",
        description = "/login <user> <password>",
        requiredArgs = 2
)
public class LoginCommand extends Command {
    public LoginCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        //login logic

        //eventually...
        SnowFinance.instance.getController().setCommandMenu(new MainCommandMenu());
    }
}
