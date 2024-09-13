package com.github.adrjo.gui;

import com.github.adrjo.gui.scenes.WelcomeScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class GuiRenderer extends Application {

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
