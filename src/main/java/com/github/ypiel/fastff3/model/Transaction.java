package com.github.ypiel.fastff3.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
public class Transaction {
    private TransactionType type;
    private Account fromAccount;
    private Account toAccount;
    private Double amount;
    private String description;
    private Category category;
    private List<String> tags = new ArrayList<>();

    public Transaction(TransactionType type, Account fromAccount, Account toAccount, double amount, String description, Category category, String tags) {
        this.type = type;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.setTagsFromString(tags);
    }

    private void setTagsFromString(String stags){
        String[] elements = Optional.ofNullable(stags).orElse("").split(" ");
        tags.clear();
        Collections.addAll(tags, elements);
    }
}
