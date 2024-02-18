package com.github.ypiel.fastff3;
import com.github.ypiel.fastff3.model.Transaction;
import com.github.ypiel.fastff3.model.TransactionType;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FastFF3 extends Application  {

    @Override
    public void start(Stage stage) {
        TableView<Transaction> tableView = new TableView<>();
        ObservableList<Transaction> data = FXCollections.observableArrayList();

        TableColumn<Transaction, TransactionType> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<Transaction, TransactionType>("type"));
        typeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(TransactionType.values()));
        typeColumn.setOnEditCommit(event -> {
            Transaction transaction = event.getRowValue();
            transaction.getType().set(event.getNewValue());
        });

        TableColumn<Transaction, String> fromAccountColumn = new TableColumn<>("From Account");
        TableColumn<Transaction, String> toAccountColumn = new TableColumn<>("To Account");
        TableColumn<Transaction, Double> amount = new TableColumn<>("Amount");
        TableColumn<Transaction, String> category = new TableColumn<>("Category");
        TableColumn<Transaction, String> tags = new TableColumn<>("Tags");

        tableView.getColumns().addAll(typeColumn, fromAccountColumn, toAccountColumn, amount, category, tags);
        tableView.setItems(data);
        tableView.setEditable(true);

        data.add(new Transaction(TransactionType.WITHDRAWAL, "", "", 0.0, "", "", ""));

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
