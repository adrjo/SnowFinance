package com.github.adrjo.commands.impl.account;

import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.menus.AccountCommandMenu;
import com.github.adrjo.fileloading.SwedbankFileLoader;
import com.github.adrjo.fileloading.TransactionFileLoader;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.RegisterCommand;

import java.io.File;
import java.io.IOException;

@ImplementsMenu(AccountCommandMenu.class)
@RegisterCommand(
        name = "import",
        description = "Imports transactions from Swedbank\n"
                + "Usage: import path/to/transactionsexport.csv",
        requiredArgs = 1
)
public class ImportCommand extends Command {

    @Deprecated
    public ImportCommand() {
        super(
                "import",
                "Imports transactions from Swedbank\n"
                        + "Usage: import path/to/transactionsexport.csv",
                1
        );
    }

    public ImportCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        TransactionFileLoader loader = new SwedbankFileLoader();
        try {
            int count = loader.load(new File(args[0]));
            System.out.println("Successfully imported " + count + " transactions");
        } catch (IOException e) {
            System.err.println("Error parsing export: " + e.getMessage());
        }
    }
}
