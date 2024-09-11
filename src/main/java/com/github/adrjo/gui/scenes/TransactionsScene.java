package com.github.adrjo.gui.scenes;

import com.github.adrjo.Helper;
import com.github.adrjo.SnowFinance;
import com.github.adrjo.gui.TransactionDisplay;
import com.github.adrjo.transactions.Transaction;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.text.ParseException;

public class TransactionsScene extends Scene {
    public TransactionsScene() {
        super(new VBox(20), 400, 500);

        render();
    }

    private void render() {
        Label title = Helper.getTitle("Transactions");
        TableView<TransactionDisplay> table = new TableView<>();
        table.setMaxWidth(600);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
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

        addTransFields.setStyle("-fx-alignment: center;");

        Button newTransaction = new Button("New Transaction");
        newTransaction.setOnAction(e -> addTransFields.setVisible(true));

        VBox layout = (VBox) this.getRoot();
        layout.getChildren().addAll(title, table, addTransFields, newTransaction);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50px;");
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
}
