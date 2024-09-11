package com.github.adrjo.gui.scenes;

import com.github.adrjo.Helper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WelcomeScene extends Scene {

    public WelcomeScene(Stage stage) {
        super(new VBox(20), 400, 500);

        render(stage);
    }

    private void render(Stage stage) {
        // Create the welcome text
        Label welcomeLabel = Helper.getTitle("Welcome to SnowFinance");

        // Create the buttons
        Button transactionsButton = new Button("Transactions");
        Button addTransactionButton = new Button("Add Transaction");
        Button removeTransactionButton = new Button("Remove Transaction");

        // Set button actions
        transactionsButton.setOnAction(e -> {
            stage.setScene(new TransactionsScene());
        });
//        addTransactionButton.setOnAction(e -> addTransaction());
//        removeTransactionButton.setOnAction(e -> removeTransaction());

        // Create a layout and add the elements
        VBox layout = (VBox) this.getRoot();
        layout.setStyle("-fx-alignment: center; -fx-padding: 50px;");
        layout.getChildren().addAll(welcomeLabel, transactionsButton, addTransactionButton, removeTransactionButton);


    }
}
