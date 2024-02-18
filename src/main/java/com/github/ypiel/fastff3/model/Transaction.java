package com.github.ypiel.fastff3.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;

@Data
public class Transaction {
    private TransactionType type;
    private Account fromAccount;
    private Account toAccount;
    private Double amount;
    private String description;
    private String category;
    private String tags;

    public Transaction(TransactionType type, Account fromAccount, Account toAccount, double amount, String description, String category, String tags) {
        this.type = type;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.tags = tags;
    }
}
