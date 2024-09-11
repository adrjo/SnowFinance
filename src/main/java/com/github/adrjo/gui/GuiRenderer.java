package com.github.adrjo.gui;

import com.github.adrjo.Helper;
import com.github.adrjo.gui.scenes.TransactionsScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiRenderer extends Application {

    public void start() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        // Create the welcome text
        Label welcomeLabel = Helper.getTitle("Welcome to SnowFinance");

        // Create the buttons
        Button transactionsButton = new Button("Transactions");
        Button addTransactionButton = new Button("Add Transaction");
        Button removeTransactionButton = new Button("Remove Transaction");

        // Set button actions
        transactionsButton.setOnAction(e -> {
            primaryStage.setScene(new TransactionsScene(primaryStage));
        });
        addTransactionButton.setOnAction(e -> addTransaction());
        removeTransactionButton.setOnAction(e -> removeTransaction());

        // Create a layout and add the elements
        VBox layout = new VBox(20); // 20 is the spacing between elements
        layout.setStyle("-fx-alignment: center; -fx-padding: 50px;");
        layout.getChildren().addAll(welcomeLabel, transactionsButton, addTransactionButton, removeTransactionButton);

        // Create the scene and set it in the stage
        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setTitle("Transaction Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addTransaction() {
        // Implement this method to add a new transaction
        System.out.println("Add Transaction Clicked");
    }

    private void removeTransaction() {
        // Implement this method to remove a transaction
        System.out.println("Remove Transaction Clicked");
    }
}
