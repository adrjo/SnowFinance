package com.github.adrjo.commands.impl.account;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.AccountCommandMenu;
import com.github.adrjo.commands.menus.SummaryCommandMenu;

@ImplementsMenu(AccountCommandMenu.class)
@RegisterCommand(
        name = "summary",
        description = "View full transaction summary (balance, expenses, income) or optionally by year, month, day or week"
)
public class SummaryCommand extends Command {

    @Deprecated
    public SummaryCommand() {
        super("summary", "View full transaction summary (balance, expenses, income) or optionally by year, month, day or week");
    }

    public SummaryCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        SnowFinance.instance.getController().setCommandMenu(new SummaryCommandMenu());
    }
}
