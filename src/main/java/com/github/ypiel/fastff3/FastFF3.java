package com.github.ypiel.fastff3;

import com.github.ypiel.fastff3.fx.AccountStringConverter;
import com.github.ypiel.fastff3.fx.CategoryStringConverter;
import com.github.ypiel.fastff3.fx.TagsStringConverter;
import com.github.ypiel.fastff3.model.Account;
import com.github.ypiel.fastff3.model.Category;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FastFF3 extends Application {

    private List<Account> assetAccounts = new ArrayList<>();
    private List<Account> expenseAccounts = new ArrayList<>();
    private List<Account> revenueAccounts = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    private final ObservableList<Account> fromAccountObservableList = FXCollections.observableArrayList();
    private final ObservableList<Account> toAccountObservableList = FXCollections.observableArrayList();
    private final ObservableList<Category> categoryObservableList = FXCollections.observableArrayList();

    private void mock() {
        Collections.addAll(assetAccounts, new Account("1", "CHEQUE"), new Account("2", "BLEU"));
        Collections.addAll(expenseAccounts, new Account("3", "CHEQUE COMMUN"), new Account("4", "CAROLE"));
        Collections.addAll(revenueAccounts, new Account("5", "PEA"));
        Collections.addAll(categories, new Category("1", "MyCateg"), new Category("2", "Abonnements"));

        fromAccountObservableList.addAll(assetAccounts);
        toAccountObservableList.addAll(expenseAccounts);
        categoryObservableList.addAll(categories);
    }

    @Override
    public void start(Stage stage) {
        mock();

        TableView<Transaction> tableView = new TableView<>();
        tableView.setEditable(true);
        ObservableList<Transaction> data = FXCollections.observableArrayList();

        TableColumn<Transaction, TransactionType> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(TransactionType.values()));

        TableColumn<Transaction, Account> fromAccountColumn = new TableColumn<>("From Account");
        fromAccountColumn.setCellValueFactory(new PropertyValueFactory<>("fromAccount"));
        AccountStringConverter fromAccountStringConverter = new AccountStringConverter(fromAccountObservableList);
        fromAccountColumn.setCellFactory(ComboBoxTableCell.forTableColumn(fromAccountStringConverter, fromAccountObservableList));

        TableColumn<Transaction, Account> toAccountColumn = new TableColumn<>("To Account");
        toAccountColumn.setCellValueFactory(new PropertyValueFactory<>("toAccount"));
        AccountStringConverter toAccountStringConverter = new AccountStringConverter(toAccountObservableList);
        toAccountColumn.setCellFactory(ComboBoxTableCell.forTableColumn(toAccountStringConverter, toAccountObservableList));

        TableColumn<Transaction, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        TableColumn<Transaction, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());


        TableColumn<Transaction, Category> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        CategoryStringConverter categoryStringConverter = new CategoryStringConverter(categoryObservableList);
        categoryColumn.setCellFactory(ComboBoxTableCell.forTableColumn(categoryStringConverter, categoryObservableList));


        TableColumn<Transaction, List<String>> tagsColumn = new TableColumn<>("Tags");
        tagsColumn.setCellValueFactory(new PropertyValueFactory<>("tags"));
        TagsStringConverter tagsStringConverter = new TagsStringConverter();
        tagsColumn.setCellFactory(TextFieldTableCell.forTableColumn(tagsStringConverter));

        tableView.getColumns().addAll(typeColumn, fromAccountColumn, toAccountColumn, amountColumn, descriptionColumn, categoryColumn, tagsColumn);
        tableView.setItems(data);
        tableView.setEditable(true);

        Transaction first = new Transaction(TransactionType.WITHDRAWAL,
                assetAccounts.get(0),
                expenseAccounts.get(0),
                0.0,
                "",
                categories.get(0),
                "");
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
