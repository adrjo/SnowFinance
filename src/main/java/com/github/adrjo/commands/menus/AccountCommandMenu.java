package com.github.adrjo.commands.menus;

import com.github.adrjo.SnowFinance;

public class AccountCommandMenu extends CommandMenu {

    public AccountCommandMenu() {
        System.out.println("Entered '" + SnowFinance.instance.getAccountManager().getActiveAccount().getName() + "'!");
    }
}
