package com.github.ypiel.fastff3.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Data;

import java.time.LocalDate;
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

    private LocalDate date;

    public Transaction(TransactionType type, Account fromAccount, Account toAccount, double amount, String description, Category category, String tags, LocalDate date) {
        this.type = type;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.setTagsFromString(tags);
        this.date = date;
    }

    private void setTagsFromString(String stags){
        String[] elements = Optional.ofNullable(stags).orElse("").split(" ");
        tags.clear();
        Collections.addAll(tags, elements);
    }
}
