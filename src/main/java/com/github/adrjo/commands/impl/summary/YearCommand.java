package com.github.adrjo.commands.impl.summary;

import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.impl.ImplementsMenu;
import com.github.adrjo.commands.menus.impl.SummaryCommandMenu;

@ImplementsMenu(SummaryCommandMenu.class)
public class YearCommand extends Command {
    public YearCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }


}
