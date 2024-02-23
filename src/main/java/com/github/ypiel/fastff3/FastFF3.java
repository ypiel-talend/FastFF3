package com.github.ypiel.fastff3;

import com.github.ypiel.fastff3.fx.AccountStringConverter;
import com.github.ypiel.fastff3.fx.CategoryStringConverter;
import com.github.ypiel.fastff3.fx.LocalDateStringConverter;
import com.github.ypiel.fastff3.fx.TagsStringConverter;
import com.github.ypiel.fastff3.model.Account;
import com.github.ypiel.fastff3.model.Category;
import com.github.ypiel.fastff3.model.Transaction;
import com.github.ypiel.fastff3.model.TransactionType;
import com.github.ypiel.fastff3.service.UIService;
import com.github.ypiel.fastff3.spring.FastFF3Config;
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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class FastFF3 extends Application {

    private UIService uiService;

    private final ObservableList<Account> fromAccountObservableList = FXCollections.observableArrayList();
    private final ObservableList<Account> toAccountObservableList = FXCollections.observableArrayList();
    private final ObservableList<Category> categoryObservableList = FXCollections.observableArrayList();

    private void mock() {
        fromAccountObservableList.addAll(uiService.getAssetAccounts());
        toAccountObservableList.addAll(uiService.getExpenseAccounts());
        categoryObservableList.addAll(uiService.getCategories());
    }

    @Override
    public void start(Stage stage) {
        initSpring();
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

        TableColumn<Transaction, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        LocalDateStringConverter localDateStringConverter = new LocalDateStringConverter(DateTimeFormatter.ofPattern("dd/MM/yyyy"), DateTimeFormatter.ofPattern("ddMMyyyy"));
        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn(localDateStringConverter));

        tableView.getColumns().addAll(typeColumn, fromAccountColumn, toAccountColumn, amountColumn, descriptionColumn, categoryColumn, tagsColumn, dateColumn);
        tableView.setItems(data);
        tableView.setEditable(true);

        Transaction first = new Transaction(TransactionType.WITHDRAWAL,
                uiService.getAssetAccounts().get(0),
                uiService.getExpenseAccounts().get(0),
                0.0,
                "",
                uiService.getCategories().get(0),
                "",
                LocalDate.now());
        data.add(first);

        VBox root = new VBox(tableView);
        Scene scene = new Scene(root, 800, 600);

        stage.setScene(scene);
        stage.setTitle("Fast Firefly III");
        stage.show();
    }

    private void initSpring() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FastFF3Config.class);
        this.uiService = context.getBean(UIService.class);
    }

    public static void main(String[] args) {
        launch();
    }

}
