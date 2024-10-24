package com.github.adrjo.commands.impl;

import com.github.adrjo.fileloading.impl.SwedbankFileLoader;
import com.github.adrjo.fileloading.TransactionFileLoader;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.RegisterCommand;

import java.io.File;
import java.io.IOException;

@RegisterCommand(
        name = "import",
        description = "Imports transactions from Swedbank\n"
                + "Usage: import path/to/transactionsexport.csv",
        requiredArgs = 1
)
public class ImportCommand extends Command {

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
