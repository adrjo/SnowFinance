package com.github.adrjo.commands.impl.main;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.accounts.Account;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.LoggedOutCommandMenu;
import com.github.adrjo.commands.menus.MainCommandMenu;
import com.github.adrjo.users.User;
import com.github.adrjo.util.Helper;

import java.util.Set;

@ImplementsMenu(MainCommandMenu.class)
@RegisterCommand(
        name = "list",
        description = "Lists all your accounts, or accounts you are a member of"
)
public class ListAccountsCommand extends Command {

    public ListAccountsCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);
        User user = SnowFinance.instance.getUserManager().getLoggedInUser();
        if (user == null) {
            System.err.println("You're not logged in");
            SnowFinance.instance.getController().setCommandMenu(new LoggedOutCommandMenu());
            return;
        }

        Set<Account> accounts = SnowFinance.instance.getAccountManager().getAccountsForUser(user.getId());

        accounts.forEach(account -> System.out.println(Helper.formattedPrint(account)));
        System.out.println();
    }
}