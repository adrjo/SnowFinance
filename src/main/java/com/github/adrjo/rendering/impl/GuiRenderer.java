package com.github.adrjo.rendering.impl;

import com.github.adrjo.rendering.Renderer;
import com.github.adrjo.rendering.impl.gui.scenes.WelcomeScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class GuiRenderer extends Application implements Renderer {

    @Override
    public void startRenderer() {
        this.start();
    }

    @Override
    public void stopRenderer() {
        try {
            this.stop();
        } catch (Exception e) {

        }
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
