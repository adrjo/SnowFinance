package com.github.adrjo.commands.impl.main;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.accounts.Account;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.MainCommandMenu;
import com.github.adrjo.users.User;
import com.github.adrjo.util.Helper;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
        final User loggedInUser = super.validateLoggedIn();
        List<Account> accounts = super.validateUserAccounts(loggedInUser);

        System.out.println("Select which account to remove: ");
        accounts.forEach(account -> System.out.println(Helper.formattedPrint(account)));
        int input = super.getIntegerInput();

        Optional<Account> optSelectedAccount = accounts.stream().filter(account -> account.getId() == input).findAny();
        if (!optSelectedAccount.isPresent()) {
            System.err.println("You do not own that account.");
            return;
        }
        Account selectedAccount = optSelectedAccount.get();

        if(!SnowFinance.instance.getAccountManager().removeAccount(selectedAccount)) {
            System.err.println("Failed to remove account");
            return;
        }

        System.out.println("Successfully removed '" + selectedAccount.getName() +"'");
    }
}
