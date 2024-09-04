package com.github.adrjo.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiRenderer extends Application {

    public void start() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        // Create the welcome text
        Label welcomeLabel = new Label("Welcome to SnowFinance");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Create the buttons
        Button transactionsButton = new Button("Transactions");
        Button addTransactionButton = new Button("Add Transaction");
        Button removeTransactionButton = new Button("Remove Transaction");

        // Set button actions
        transactionsButton.setOnAction(e -> showTransactions());
        addTransactionButton.setOnAction(e -> addTransaction());
        removeTransactionButton.setOnAction(e -> removeTransaction());

        // Create a layout and add the elements
        VBox layout = new VBox(20); // 20 is the spacing between elements
        layout.setStyle("-fx-alignment: center; -fx-padding: 50px;");
        layout.getChildren().addAll(welcomeLabel, transactionsButton, addTransactionButton, removeTransactionButton);

        // Create the scene and set it in the stage
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Transaction Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showTransactions() {
        // Implement this method to show the list of transactions
        System.out.println("Show Transactions Clicked");
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
