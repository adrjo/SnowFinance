package com.github.adrjo.commands.impl.main;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.accounts.Account;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.AccountCommandMenu;
import com.github.adrjo.commands.menus.MainCommandMenu;
import com.github.adrjo.users.User;
import com.github.adrjo.util.Helper;

import java.util.List;
import java.util.Optional;

@ImplementsMenu(MainCommandMenu.class)
@RegisterCommand(
        name = "enter",
        description = "Enters an account"
)
public class EnterAccountCommand extends Command {
    public EnterAccountCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        final User loggedInUser = super.validateLoggedIn();
        List<Account> accounts = super.validateUserAccounts(loggedInUser, false);

        System.out.println("Select which account to enter: ");
        accounts.forEach(account -> System.out.println(Helper.formattedPrint(account)));
        int input = super.getIntegerInput();

        Optional<Account> optSelectedAccount = accounts.stream().filter(account -> account.getId() == input).findAny();
        if (!optSelectedAccount.isPresent()) {
            System.err.println("You do not own that account.");
            return;
        }
        Account selected = optSelectedAccount.get();

        SnowFinance.instance.getAccountManager().setActiveAccount(selected);
        SnowFinance.instance.getController().setCommandMenu(new AccountCommandMenu());
    }
}