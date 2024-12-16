package com.github.adrjo.control.gui;

import com.github.adrjo.commands.menus.CommandMenu;
import com.github.adrjo.control.Controller;
import com.github.adrjo.control.gui.scenes.WelcomeScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class GuiController extends Application implements Controller {

    @Override
    public void startController() {
        this.start();
    }

    @Override
    public void stopController() {
        try {
            this.stop();
        } catch (Exception e) {

        }
    }

    @Override
    public CommandMenu getCommandMenu() {
        throw new IllegalArgumentException("Unsupported.");
    }

    @Override
    public void setCommandMenu(CommandMenu menu) {
        throw new IllegalArgumentException("Unsupported.");
    }

    public void start() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Transaction Manager");
        stage.setScene(new WelcomeScene(stage));
        stage.show();
        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });
    }


}
