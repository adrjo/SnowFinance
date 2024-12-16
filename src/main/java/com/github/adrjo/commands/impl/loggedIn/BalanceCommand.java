package com.github.adrjo.commands.impl.loggedIn;

import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.menus.MainCommandMenu;
import com.github.adrjo.util.Helper;
import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.RegisterCommand;

import java.text.ParseException;
import java.util.Date;

@ImplementsMenu(MainCommandMenu.class)
@RegisterCommand(
        name = "bal",
        description = "Returns your current balance OR your balance at a specific [Date] (yyyy-MM-dd.hh:mm)"
)
public class BalanceCommand extends Command {

    @Deprecated
    public BalanceCommand() {
        super("bal", "Returns your current balance OR your balance at a specific [Date] (yyyy-MM-dd.hh:mm)");
    }

    public BalanceCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
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
