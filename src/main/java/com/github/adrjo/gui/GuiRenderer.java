package com.github.adrjo.gui;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.transactions.Transaction;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiRenderer extends Application {

    public void start() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        // Create the welcome text
        Label welcomeLabel = getTitle("Welcome to SnowFinance");

        // Create the buttons
        Button transactionsButton = new Button("Transactions");
        Button addTransactionButton = new Button("Add Transaction");
        Button removeTransactionButton = new Button("Remove Transaction");

        // Set button actions
        transactionsButton.setOnAction(e -> showTransactions(primaryStage));
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

    private Label getTitle(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        return label;
    }

    private void showTransactions(Stage stage) {
        Label title = getTitle("Transactions");
        TableView<TransactionDisplay> table = new TableView<>();
        table.setEditable(false);

        TableColumn<TransactionDisplay, Integer> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<TransactionDisplay, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("desc"));
        TableColumn<TransactionDisplay, String> dateTime = new TableColumn<>("Date");
        dateTime.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<TransactionDisplay, Double> amount = new TableColumn<>("Amount");
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        table.getColumns().addAll(id, name, dateTime, amount);

        SnowFinance.instance.getTransactionManager().getTransactions().forEach((tid, transaction) -> {
            table.getItems().add(new TransactionDisplay(tid, transaction));
        });

        VBox layout = new VBox(20);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50px;");
        layout.getChildren().addAll(title, table);
        Scene transactionScene = new Scene(layout, 400, 300);
        stage.setScene(transactionScene);
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
