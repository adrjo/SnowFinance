package com.github.adrjo.commands.impl;

import com.github.adrjo.Helper;
import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.RegisterCommand;
import com.github.adrjo.transactions.Transaction;

import java.text.ParseException;
import java.util.Date;

@RegisterCommand(
        name = "add",
        description = "args: [name] [amt in SEK] [date in format " + Helper.DATE_AND_TIME + " (or blank for current time)]\n"
                + "Example: `add Willys 500 2024-08-30.20:30`",
        requiredArgs = 2
)
public class AddTransactionCommand extends Command {

    @Override
    public void exec(String[] args) {
        super.exec(args);

        Date date = new Date(System.currentTimeMillis());
        // has date arg
        if (args.length == 3) {
            try {
                date = Helper.DATE_FORMAT.parse(args[2]);
            } catch (ParseException e) {
                System.err.println(this.getName() + ": date must be in format " + Helper.DATE_AND_TIME + " or be left blank for current date and time");
                return;
            }
        }

        final String name = args[0];
        final double amt = Double.parseDouble(args[1]);
        final Transaction trans = new Transaction(name, amt, date.getTime());
        SnowFinance.instance.getTransactionManager().add(trans);
        System.out.println(this.getName() + ": successfully added transaction " + trans);
    }
}
