package com.github.ypiel.fastff3.fx;

import com.github.ypiel.fastff3.model.Account;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

import java.util.Optional;

public class AccountStringConverter extends IdLabelStringConverter<Account> {

    public AccountStringConverter(ObservableList<Account> observableList) {
        super(observableList);
    }

}
