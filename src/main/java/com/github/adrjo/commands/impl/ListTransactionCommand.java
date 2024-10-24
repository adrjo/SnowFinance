package com.github.adrjo.commands.impl;

import com.github.adrjo.util.Helper;
import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.RegisterCommand;
import com.github.adrjo.util.TransactionUtil;

import java.text.ParseException;
import java.util.Date;

@RegisterCommand(
        name = "list",
        description = "List saved transactions, optionally by [Date]"
)
public class ListTransactionCommand extends Command {

    @Override
    public void exec(String[] args) {
        if (args.length > 0) {
            try {
                Date date = Helper.DATE_FORMAT.parse(args[0]);
                SnowFinance.instance.getTransactionManager()
                        .getTransactionsBefore(date.getTime())
                        .forEach(TransactionUtil::printTransactionInfo);
            } catch (ParseException e) {
                System.err.println(this.getName() + ": date must be in format " + Helper.DATE_AND_TIME);
            }
            return;
        }

        SnowFinance.instance.getTransactionManager()
                .getTransactions()
                .forEach(TransactionUtil::printTransactionInfo);
    }
}
