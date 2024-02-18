package com.github.ypiel.fastff3.fx;

import com.github.ypiel.fastff3.model.Account;
import com.github.ypiel.fastff3.model.IdLabel;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

import java.util.Optional;

public class IdLabelStringConverter<T extends IdLabel> extends StringConverter<T> {

    private ObservableList<T> observableList;

    public IdLabelStringConverter(ObservableList<T> observableList){
        this.observableList = observableList;
    }

    @Override
    public String toString(T idLabel) {
        return idLabel.getLabel();
    }

    @Override
    public T fromString(String s) {
        Optional<T> opt = observableList.stream().filter(a -> a.getLabel().equals(s)).findFirst();
        return opt.orElse(null);
    }
}
