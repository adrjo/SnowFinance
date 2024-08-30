package com.github.adrjo.commands.impl;

import com.github.adrjo.Helper;
import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;

import java.text.ParseException;
import java.util.Date;

public class BalanceCommand extends Command {

    public BalanceCommand() {
        super("bal", "returns your current balance OR your balance at a specific [Date] (yyyy-MM-dd.hh:mm)");
    }

    @Override
    public void exec(String[] args) {
        if (args.length > 0) {
            try {
                Date date = Helper.DATE_FORMAT.parse(args[0]);
                System.out.printf("%s: Balance at %s = %.2f SEK\n",
                        this.getName(),
                        Helper.DATE_FORMAT.format(date),
                        SnowFinance.instance.getTransactionManager().getBalanceAt(date.getTime()));
            } catch (ParseException e) {
                System.err.println(this.getName() + ": date must be in format " + Helper.DATE_AND_TIME);
            }
            return;
        }
        System.out.printf("%s: Current balance = %.2f SEK\n",
                this.getName(),
                SnowFinance.instance.getTransactionManager().getBalance());
    }
}
