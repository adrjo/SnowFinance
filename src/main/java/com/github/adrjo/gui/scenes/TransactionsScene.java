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

        updateTable(table);

        // For error feedback
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);

        //Add transaction inputs
        //name, amount, date(optional)
        GridPane addTransFields = new GridPane();
        TextField nameField = new TextField("");
        nameField.setPromptText("Name");
        TextField amountField = new TextField("");
        amountField.setPromptText("Amount");
        TextField dateField = new TextField("");
        dateField.setPromptText("Date");
        Button addTransaction = new Button("Add");
        addTransaction.setOnAction(e -> {
            try {
                String desc = nameField.getText();
                double amt = Double.parseDouble(amountField.getText());
                String date = dateField.getText();
                if (date.isBlank()) {
                    SnowFinance.instance.getTransactionManager().addNow(desc, amt);
                } else {
                    SnowFinance.instance.getTransactionManager().add(desc, amt, Helper.DATE_FORMAT.parse(date).getTime());
                }
                clearTextFields(nameField, amountField, dateField);
                errorLabel.setVisible(false);
            } catch (ParseException ex) {
                errorLabel.setText("Error: date must be written in format " + Helper.DATE_AND_TIME);
                errorLabel.setVisible(true);
            } catch (NumberFormatException ex) {
                errorLabel.setText("Error: amount: type a number");
                errorLabel.setVisible(true);
            }
            updateTable(table);
        });

        addTransFields.addRow(0, nameField, amountField, dateField, addTransaction);
        addTransFields.setVisible(false);

        addTransFields.setStyle("-fx-alignment: center;");

        Button newTransaction = new Button("New Transaction");
        newTransaction.setOnAction(e -> addTransFields.setVisible(true));

        VBox layout = (VBox) this.getRoot();
        layout.getChildren().addAll(title, table, addTransFields, newTransaction, errorLabel);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50px;");
    }

    private void clearTextFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void updateTable(TableView<TransactionDisplay> table) {
        table.getItems().clear();
        SnowFinance.instance.getTransactionManager().getTransactions().forEach((tid, transaction) -> {
            table.getItems().add(new TransactionDisplay(tid, transaction));
        });
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
