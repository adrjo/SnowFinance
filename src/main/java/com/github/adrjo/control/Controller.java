package com.github.adrjo.control;

import com.github.adrjo.commands.menus.CommandMenu;

public interface Controller {

    void startController();

    void stopController();

    CommandMenu getCommandMenu();

    void setCommandMenu(CommandMenu menu);
}
