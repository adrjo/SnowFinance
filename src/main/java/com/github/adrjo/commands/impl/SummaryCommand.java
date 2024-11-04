package com.github.adrjo.commands.impl;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.impl.ImplementsMenu;
import com.github.adrjo.commands.annotations.impl.RegisterCommand;
import com.github.adrjo.commands.menus.impl.MainCommandMenu;
import com.github.adrjo.commands.menus.impl.SummaryCommandMenu;

@ImplementsMenu(MainCommandMenu.class)
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
