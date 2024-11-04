package com.github.adrjo.commands.menus.impl;

import com.github.adrjo.commands.menus.CommandMenu;

public class SummaryCommandMenu extends CommandMenu {

    public SummaryCommandMenu() {
        System.out.println("""
                            Welcome to reports!
                            Show reports by:
                            1. Year
                            2. Year and Month
                            3. Year, Month and Day
                            4. Year and Week
                            5. Full reports
                            """);
    }
}
