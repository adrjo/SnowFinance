package com.github.adrjo.commands.impl.summary;

import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.impl.ImplementsMenu;
import com.github.adrjo.commands.menus.impl.SummaryCommandMenu;

@ImplementsMenu(SummaryCommandMenu.class)
public class YearWeekCommand extends Command {
    public YearWeekCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }
}
