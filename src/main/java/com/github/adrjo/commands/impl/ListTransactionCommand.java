package com.github.adrjo.commands.impl;

import com.github.adrjo.Helper;
import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;

import java.util.Date;

public class ListTransactionCommand extends Command {

    public ListTransactionCommand() {
        super("list", "List saved transactions, optionally by [Date]");
    }

    @Override
    public void exec(String[] args) {
        if (args.length > 0) {
            return;
        }

        SnowFinance.instance.getTransactionManager().getTransactions().forEach((id, transaction) -> {
            System.out.printf("%d | %s | %.2f | %s\n",
                    id,
                    transaction.desc(),
                    transaction.amt(),
                    Helper.DATE_FORMAT.format(new Date(transaction.timestamp()))
            );
        });
    }
}
