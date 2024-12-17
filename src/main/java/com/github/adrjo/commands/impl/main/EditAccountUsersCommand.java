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

@ImplementsMenu(MainCommandMenu.class)
@RegisterCommand(
        name = "edit",
        description = "Add or remove account users"
)
public class EditAccountUsersCommand extends Command {
    public EditAccountUsersCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);
        final User loggedInUser = super.validateLoggedIn();
        List<Account> accounts = super.validateUserAccounts(loggedInUser, true);

        System.out.println("Select which account to modify: ");
        accounts.forEach(account -> System.out.println(Helper.formattedPrint(account)));
        int input = super.getIntegerInput();
        Optional<Account> optSelectedAccount = accounts.stream().filter(account -> account.getId() == input).findAny();
        if (!optSelectedAccount.isPresent()) {
            System.err.println("You do not own that account.");
            return;
        }

        System.out.println("Would you like to remove(0) or add(1) a user?");
        boolean add = (super.getIntegerInput() != 0);

        Account selectedAccount = optSelectedAccount.get();
        List<User> users = SnowFinance.instance.getUserManager().getAllUsers().stream()
                .filter(user -> {
                    if (add) {
                        return !selectedAccount.getUsers().contains(user);
                    }
                    return selectedAccount.getUsers().contains(user) && user.getId() != selectedAccount.getOwnerId();
                })
                .toList();

        if (users.isEmpty()) {
            System.err.println("No users to " + (add ? "add." : "remove."));
            return;
        }

        System.out.println("Select a user: ");
        users.forEach(user -> System.out.printf("%d. %s%n", user.getId(), user.getName()));
        int userInput = super.getIntegerInput();

        Optional<User> userOptional = users.stream().filter(user -> user.getId() == userInput).findAny();

        if (!userOptional.isPresent()) {
            System.err.println("Invalid user.");
            return;
        }

        if (add) {
            if (!SnowFinance.instance.getAccountManager().addUserToAccount(userOptional.get().getId(), selectedAccount.getId())) {
                System.err.println("Failed to add user to account.");
                return;
            }
        } else {
            if (!SnowFinance.instance.getAccountManager().removeUserFromAccount(userOptional.get().getId(), selectedAccount.getId())) {
                System.err.println("Failed to remove user from account.");
                return;
            }
        }

        System.out.println("Success!");
    }
}
