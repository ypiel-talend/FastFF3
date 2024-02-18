package com.github.ypiel.fastff3;

import com.github.ypiel.fastff3.fx.AccountStringConverter;
import com.github.ypiel.fastff3.model.Account;
import com.github.ypiel.fastff3.model.Transaction;
import com.github.ypiel.fastff3.model.TransactionType;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastFF3 extends Application {

    private List<Account> assetAccounts = new ArrayList<>();
    private List<Account> expenseAccount = new ArrayList<>();
    private List<Account> revenueAccount = new ArrayList<>();

    private final ObservableList<Account> fromAccountObservableList = FXCollections.observableArrayList();
    private final ObservableList<Account> toAccountObservableList = FXCollections.observableArrayList();

    private void mock() {
        Collections.addAll(assetAccounts, new Account("1", "CHEQUE"), new Account("2", "BLEU"));
        Collections.addAll(expenseAccount, new Account("3", "CHEQUE COMMUN"), new Account("4", "CAROLE"));
        Collections.addAll(revenueAccount, new Account("5", "PEA"));

        fromAccountObservableList.addAll(assetAccounts);
        toAccountObservableList.addAll(expenseAccount);
    }

    @Override
    public void start(Stage stage) {
        mock();

        TableView<Transaction> tableView = new TableView<>();
        tableView.setEditable(true);
        ObservableList<Transaction> data = FXCollections.observableArrayList();

        TableColumn<Transaction, TransactionType> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setCellFactory(ComboBoxTableCell.<Transaction, TransactionType>forTableColumn(TransactionType.values()));

        TableColumn<Transaction, Account> fromAccountColumn = new TableColumn<>("From Account");
        fromAccountColumn.setCellValueFactory(new PropertyValueFactory<>("fromAccount"));

        AccountStringConverter fromAccountStringConverter = new AccountStringConverter(fromAccountObservableList);
        fromAccountColumn.setCellFactory(ComboBoxTableCell.forTableColumn(fromAccountStringConverter, fromAccountObservableList));

        TableColumn<Transaction, Account> toAccountColumn = new TableColumn<>("To Account");
        toAccountColumn.setCellValueFactory(new PropertyValueFactory<>("toAccount"));
        AccountStringConverter toAccountStringConverter = new AccountStringConverter(toAccountObservableList);
        toAccountColumn.setCellFactory(ComboBoxTableCell.forTableColumn(toAccountStringConverter, toAccountObservableList));

        TableColumn<Transaction, Double> amount = new TableColumn<>("Amount");
        TableColumn<Transaction, String> category = new TableColumn<>("Category");
        TableColumn<Transaction, String> tags = new TableColumn<>("Tags");

        tableView.getColumns().addAll(typeColumn, fromAccountColumn, toAccountColumn, amount, category, tags);
        tableView.setItems(data);
        tableView.setEditable(true);

        Transaction first = new Transaction(TransactionType.WITHDRAWAL, assetAccounts.get(0), expenseAccount.get(0), 0.0, "", "", "");
        data.add(first);

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 800, 600);

        stage.setScene(scene);
        stage.setTitle("Fast Firefly III");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
