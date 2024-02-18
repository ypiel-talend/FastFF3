package com.github.ypiel.fastff3.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;

@Data
public class Transaction {
    private SimpleObjectProperty<TransactionType> type;
    private SimpleStringProperty fromAccount;
    private SimpleStringProperty toAccount;
    private SimpleDoubleProperty amount;
    private SimpleStringProperty description;
    private SimpleStringProperty category;
    private SimpleStringProperty tags;

    public Transaction(TransactionType type, String fromAccount, String toAccount, double amount, String description, String category, String tags) {
        this.type = new SimpleObjectProperty<>(type);
        this.fromAccount = new SimpleStringProperty(fromAccount);
        this.toAccount = new SimpleStringProperty(toAccount);
        this.amount = new SimpleDoubleProperty(amount);
        this.description = new SimpleStringProperty(description);
        this.category = new SimpleStringProperty(category);
        this.tags = new SimpleStringProperty(tags);
    }
}
