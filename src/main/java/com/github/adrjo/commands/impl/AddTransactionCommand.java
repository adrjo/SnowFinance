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
        description = "args: [name] [amt in SEK] [date in format " + Helper.DATE_AND_TIME + " (or blank for current time)]"
)
public class AddTransactionCommand extends Command {

    @Override
    public void exec(String[] args) {
        if (args == null || args.length < 2) {
            System.err.println(this.getName() + ": " + this.getDesc());
            System.out.println("Example: `" + this.getName() + " Willys 500 2024-08-30.20:30`");
            System.out.println("or: `" + this.getName() + " Willys 500` (your current time is used for the date)");
            return;
        }
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
