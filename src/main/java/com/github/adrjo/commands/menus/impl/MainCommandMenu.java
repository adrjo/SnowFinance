package com.github.adrjo.commands.menus.impl;

import com.github.adrjo.commands.menus.CommandMenu;

public class MainCommandMenu extends CommandMenu {

    public MainCommandMenu() {
        System.out.println("Commands: " + this.getCommandList());
    }
}
