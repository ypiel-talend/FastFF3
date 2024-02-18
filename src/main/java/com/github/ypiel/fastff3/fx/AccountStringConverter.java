package com.github.ypiel.fastff3.fx;

import com.github.ypiel.fastff3.model.Account;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

import java.util.Optional;

public class AccountStringConverter extends StringConverter<Account> {

    private ObservableList<Account> observableList;

    public AccountStringConverter(ObservableList<Account> observableList){
        this.observableList = observableList;
    }

    @Override
    public String toString(Account account) {
        return account.getLabel();
    }

    @Override
    public Account fromString(String s) {
        Optional<Account> opt = observableList.stream().filter(a -> a.getLabel().equals(s)).findFirst();
        return opt.orElse(null);
    }
}
