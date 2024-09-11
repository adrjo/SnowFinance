package com.github.adrjo.gui;

import com.github.adrjo.Helper;
import com.github.adrjo.SnowFinance;
import com.github.adrjo.transactions.Transaction;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.text.ParseException;

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
        Scene scene = new Scene(layout, 500, 400);
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

        // Add delete button
        TableColumn<TransactionDisplay, Void> actionColumn = addButton(table);

        table.getColumns().addAll(id, name, dateTime, amount, actionColumn);


        SnowFinance.instance.getTransactionManager().getTransactions().forEach((tid, transaction) -> {
            table.getItems().add(new TransactionDisplay(tid, transaction));
        });

        //Add transaction inputs
        //name, amount, date(optional)
        GridPane addTransFields = new GridPane();
        TextField nameField = new TextField("Name");
        TextField amountField = new TextField("Amount");
        TextField dateField = new TextField("Date");
        Button addTransaction = new Button("Add");
        addTransaction.setOnAction(e -> {
            try {
                String desc = nameField.getText();
                String amt = amountField.getText();
                String date = dateField.getText();
                System.out.println("Adding " + new Transaction(desc, Double.parseDouble(amt), Helper.DATE_FORMAT.parse(date).getTime()));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        });

        addTransFields.addRow(0, nameField, amountField, dateField, addTransaction);
        addTransFields.setVisible(false);


        Button newTransaction = new Button("New Transaction");
        newTransaction.setOnAction(e -> addTransFields.setVisible(true));

        VBox layout = new VBox(20);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50px;");
        layout.getChildren().addAll(title, table, newTransaction, addTransFields);
        Scene transactionScene = new Scene(layout, 500, 400);
        stage.setScene(transactionScene);
    }

    private TableColumn<TransactionDisplay, Void> addButton(TableView<TransactionDisplay> table) {
        TableColumn<TransactionDisplay, Void> actionColumn = new TableColumn<>("Actions");

        Callback<TableColumn<TransactionDisplay, Void>, TableCell<TransactionDisplay, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<TransactionDisplay, Void> call(final TableColumn<TransactionDisplay, Void> param) {
                final TableCell<TransactionDisplay, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Remove");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            TransactionDisplay data = getTableView().getItems().get(getIndex());
                            table.getItems().remove(data);
                            SnowFinance.instance.getTransactionManager().remove(data.getId());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        actionColumn.setCellFactory(cellFactory);
        return actionColumn;
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
