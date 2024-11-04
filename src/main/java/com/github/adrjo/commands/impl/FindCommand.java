package com.github.adrjo.commands.impl;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.impl.ImplementsMenu;
import com.github.adrjo.commands.annotations.impl.RegisterCommand;
import com.github.adrjo.commands.menus.impl.MainCommandMenu;
import com.github.adrjo.transactions.Transaction;
import com.github.adrjo.util.TransactionUtil;

import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

@ImplementsMenu(MainCommandMenu.class)
@RegisterCommand(
        name = "find",
        description = "Find transactions by name",
        requiredArgs = 1
)
public class FindCommand extends Command {
    private boolean list = true;

    @Deprecated
    public FindCommand() {
        super("find", "Find transactions by name", 1);
    }

    public FindCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        String toSearch = String.join("", args);

        Map<Integer, Transaction> transactionsFound = SnowFinance.instance.getTransactionManager().find(toSearch);

        System.out.println("Found " + transactionsFound.size() + " transactions.");
        if (transactionsFound.size() > 10) {
            list = false;
            System.out.println("List them all? (y/n)");
            if (new Scanner(System.in).nextLine().startsWith("y")) {
                list = true;
            }
        }
        if (list) {
            transactionsFound.entrySet().stream()
                    .sorted(Comparator.comparingInt(Map.Entry::getKey))
                    .forEach(e -> TransactionUtil.printTransactionInfo(e.getKey(), e.getValue()));
        }

        System.out.println(); // padding
        TransactionUtil.printStats(transactionsFound);
    }
}
