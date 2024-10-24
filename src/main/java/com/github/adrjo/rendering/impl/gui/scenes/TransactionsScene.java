package com.github.adrjo.rendering.impl.gui.scenes;

import com.github.adrjo.Helper;
import com.github.adrjo.SnowFinance;
import com.github.adrjo.rendering.impl.gui.DateInput;
import com.github.adrjo.rendering.impl.gui.TransactionDisplay;
import com.github.adrjo.transactions.Transaction;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class TransactionsScene extends Scene {

    private CheckBox showIncome = new CheckBox("Show Income ");
    private CheckBox showOutcome = new CheckBox("Show Money Spent ");

    private Label balance = new Label("Balance: 0 SEK");
    private Label expenses = new Label("Expense: 0 SEK");
    private Label income = new Label("Income: 0 SEK");
    private DateInput activeFilter;

    public TransactionsScene() {
        super(new VBox(20), 600, 500);

        showIncome.setSelected(true);
        showOutcome.setSelected(true);
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
                    SnowFinance.instance.getTransactionManager().add(desc, amt, new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date).getTime());
                }
                clearTextFields(nameField, amountField, dateField);
                errorLabel.setVisible(false);
            } catch (ParseException ex) {
                errorLabel.setText("Error: date must be written in format yyyy-MM-dd HH:mm");
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


        GridPane checkBoxes = new GridPane();
        checkBoxes.setStyle("-fx-alignment: center;");
        showOutcome.setOnAction(e -> {
            updateTable(table);
        });
        showIncome.setOnAction(e -> {
            updateTable(table);
        });

        Dialog<DateInput> filterDialog = createFilterDialog();
        filterDialog.setResizable(true);

        Button filterButton = new Button("Filter By Date");
        filterButton.setOnAction(e -> {
            Optional<DateInput> result = filterDialog.showAndWait();
            result.ifPresent(dateInput -> {
                activeFilter = dateInput;
                title.setText("Transactions during " + updateTable(table));
            });
        });
        Button clearFilter = new Button("Clear Filter");
        clearFilter.setOnAction(e -> {
            activeFilter = null;
            updateTable(table);
            title.setText("Transactions");
        });


        checkBoxes.addRow(0, showIncome, showOutcome, filterButton, clearFilter);
        GridPane balances = new GridPane();
        // no need to add padding to the outer two, the middle one splits them up already
        expenses.setStyle("-fx-padding: 10px;");
        balances.setStyle("-fx-alignment: center; -fx-font-weight: bold;");
        balances.addRow(0, balance, expenses, income);

        VBox layout = (VBox) this.getRoot();
        layout.getChildren().addAll(title, balances, checkBoxes, table, addTransFields, newTransaction, errorLabel);
        layout.setStyle("-fx-alignment: center; -fx-padding: 50px;");
    }

    private Dialog<DateInput> createFilterDialog() {
        Dialog<DateInput> filterDialog = new Dialog<>();
        filterDialog.setTitle("Date Input Dialog");


        ComboBox<String> dropdown = new ComboBox<>();
        dropdown.getItems().addAll("Year", "Month", "Day", "Week");
        dropdown.setValue("Year");

        // Create text fields for input
        TextField year = new TextField();
        year.setPromptText("yyyy");
        TextField month = new TextField();
        month.setPromptText("MM");
        TextField day = new TextField();
        day.setPromptText("dd");
        TextField week = new TextField();
        week.setPromptText("ww");

        GridPane grid = new GridPane();
        Label select = new Label("Select");
        grid.addRow(0, select, dropdown);
        Label input = new Label("Input:");
        grid.addRow(1, input, year);

        dropdown.setOnAction(e -> {
            grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 1); // Remove the current input field
            switch (dropdown.getValue()) {
                case "Year" -> grid.addRow(1, input, year);
                case "Month" -> grid.addRow(1, input, year, month);
                case "Day" -> grid.addRow(1, input, year, month, day);
                case "Week" -> grid.addRow(1, input, year, week);
            }
        });

        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        filterDialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        filterDialog.setResultConverter(button -> {
            if (button.equals(submitButtonType)) {
                switch (dropdown.getValue()) {
                    case "Year":
                        return new DateInput(DateInput.DateType.YEAR, year.getText());
                    case "Month":
                        return new DateInput(DateInput.DateType.MONTH, year.getText(), month.getText());
                    case "Day":
                        return new DateInput(DateInput.DateType.DAY, year.getText(), month.getText(), day.getText());
                    case "Week":
                        return new DateInput(DateInput.DateType.WEEK, year.getText(), week.getText());
                }
            }

            return null;
        });


        filterDialog.getDialogPane().setContent(grid);
        return filterDialog;
    }

    private void clearTextFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private String updateTable(TableView<TransactionDisplay> table) {
        table.getItems().clear();
        String dateString = "";
        Map<Integer, Transaction> transactions = null;
        if (activeFilter != null) {
            LocalDate localDate = activeFilter.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            switch (activeFilter.getDateType()) {
                case YEAR -> {
                    dateString = new SimpleDateFormat("yyyy").format(activeFilter.getDate());
                    transactions = SnowFinance.instance.getTransactionManager().getTransactionsBetween(
                            activeFilter.getDate().getTime(),
                            localDate.plusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                    );
                }
                case MONTH -> {
                    dateString = new SimpleDateFormat("yyyy MMM").format(activeFilter.getDate());
                    transactions = SnowFinance.instance.getTransactionManager().getTransactionsBetween(
                            activeFilter.getDate().getTime(),
                            localDate.plusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                    );
                }
                case DAY -> {
                    dateString = new SimpleDateFormat("yyyy MMM dd").format(activeFilter.getDate());
                    transactions = SnowFinance.instance.getTransactionManager().getTransactionsBetween(
                            activeFilter.getDate().getTime(),
                            localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                    );
                }
                case WEEK -> {
                    dateString = new SimpleDateFormat("YYYY 'Week' ww (yyyy-MM-dd)").format(activeFilter.getDate());

                    transactions = SnowFinance.instance.getTransactionManager().getTransactionsBetween(
                            activeFilter.getDate().getTime(),
                            localDate.plusWeeks(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                    );
                }

            }
        } else {
            transactions = SnowFinance.instance.getTransactionManager().getAllTransactions();
        }

        transactions.entrySet().stream()
                .filter(entry -> {
                    if (showIncome.isSelected() && entry.getValue().amt() >= 0) {
                        return true;
                    }
                    return showOutcome.isSelected() && entry.getValue().amt() <= 0;
                })
                .forEach(entry -> {
                    int id = entry.getKey();
                    Transaction transaction = entry.getValue();
                    table.getItems().add(new TransactionDisplay(id, transaction));
                });

        updateBalances(transactions);

        return dateString;
    }

    private void updateBalances(Map<Integer, Transaction> transactions) {
        double balance = getBalanceFromTransactions(transactions);
        double expense = getExpensesForTransactions(transactions);
        double income = getIncomeForTransactions(transactions);
        this.balance.setText("Balance: " + balance + " SEK");
        this.expenses.setText("Expense: " + expense + " SEK");
        this.income.setText("Income: " + income + " SEK");
    }

    private double getBalanceFromTransactions(Map<Integer, Transaction> transactions) {
        return transactions.values().stream()
                .mapToDouble(Transaction::amt)
                .sum();
    }

    private double getExpensesForTransactions(Map<Integer, Transaction> transactions) {
        return transactions.values().stream()
                .mapToDouble(Transaction::amt)
                .filter(amt -> amt < 0)
                .sum();
    }

    private double getIncomeForTransactions(Map<Integer, Transaction> transactions) {
        return transactions.values().stream()
                .mapToDouble(Transaction::amt)
                .filter(amt -> amt > 0)
                .sum();
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
                            updateTable(table);
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
