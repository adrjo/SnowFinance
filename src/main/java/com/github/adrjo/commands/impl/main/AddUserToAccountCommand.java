package com.github.adrjo.commands.impl.main;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.accounts.Account;
import com.github.adrjo.accounts.AccountManager;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.LoggedOutCommandMenu;
import com.github.adrjo.commands.menus.MainCommandMenu;
import com.github.adrjo.users.User;
import com.github.adrjo.util.Helper;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@ImplementsMenu(MainCommandMenu.class)
@RegisterCommand(
        name = "add",
        description = "Adds a user to one of your accounts"
)
public class AddUserToAccountCommand extends Command {
    public AddUserToAccountCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        User loggedInUser = SnowFinance.instance.getUserManager().getLoggedInUser();
        if (loggedInUser == null) {
            System.err.println("You're not logged in");
            SnowFinance.instance.getController().setCommandMenu(new LoggedOutCommandMenu());
            return;
        }
        final AccountManager accManager = SnowFinance.instance.getAccountManager();

        List<Account> accounts = accManager.getAccountsForUser(loggedInUser.getId()).stream()
                .filter(Account::isOwner)
                .toList();

        if (accounts.isEmpty()) {
            System.err.println("You do not have any accounts. Create one via 'create [Name] [Description]'");
            return;
        }

        System.out.println("Select which account to add users to: ");
        accounts.forEach(account -> System.out.println(Helper.formattedPrint(account)));
        final Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        int input = Integer.parseInt(scanner.nextLine());
        Optional<Account> optSelectedAccount = accounts.stream().filter(account -> account.getId() == input).findAny();
        if (!optSelectedAccount.isPresent()) {
            System.err.println("You do not own that account.");
            return;
        }

        Account selectedAccount = optSelectedAccount.get();
        List<User> users = SnowFinance.instance.getUserManager().getAllUsers().stream()
                .filter(user -> !selectedAccount.getUsers().contains(user))
                .toList();

        if (users.isEmpty()) {
            System.err.println("No users to add.");
            return;
        }

        System.out.println("Select a user to add: ");
        users.forEach(user -> System.out.printf("%d. %s%n", user.getId(), user.getName()));

        System.out.print("> ");
        int userInput = Integer.parseInt(scanner.nextLine());
        Optional<User> userOptional = users.stream().filter(user -> user.getId() == userInput).findAny();

        if (!userOptional.isPresent()) {
            System.err.println("Invalid user.");
            return;
        }

        if (!accManager.addUserToAccount(userOptional.get().getId(), selectedAccount.getId())) {
            System.err.println("Failed to add user to account.");
            return;
        }

        System.out.println("Success!");
    }
}
