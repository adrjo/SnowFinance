package com.github.adrjo.commands.impl.loggedIn;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.LoggedOutCommandMenu;
import com.github.adrjo.commands.menus.MainCommandMenu;

@ImplementsMenu(MainCommandMenu.class)
@RegisterCommand(
        name = "logout",
        description = "Logs out of the current user"
)
public class LogoutCommand extends Command {
    public LogoutCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        SnowFinance.instance.getUserManager().logout();
        System.out.println("Logged out.");
        SnowFinance.instance.getController().setCommandMenu(new LoggedOutCommandMenu());
    }
}
