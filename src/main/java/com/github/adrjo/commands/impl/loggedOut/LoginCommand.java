package com.github.adrjo.commands.impl.loggedOut;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.LoggedOutCommandMenu;
import com.github.adrjo.commands.menus.MainCommandMenu;
import com.github.adrjo.users.User;
import com.github.adrjo.users.UserManager;

@ImplementsMenu(LoggedOutCommandMenu.class)
@RegisterCommand(
        name = "login",
        description = "login <user> <password>",
        requiredArgs = 2
)
public class LoginCommand extends Command {
    public LoginCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        final String username = args[0];
        final String password = args[1];

        final UserManager manager = SnowFinance.instance.getUserManager();
        if (!manager.userExists(username) || !manager.login(username, password)) {
            System.err.println("Invalid username or password.");
            return;
        }

        User user = manager.getLoggedInUser();
        System.out.println("Logged in! Welcome " + user.getName() + "!");
        SnowFinance.instance.getController().setCommandMenu(new MainCommandMenu());
    }
}
