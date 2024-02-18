package com.github.ypiel.fastff3.fx;

import com.github.ypiel.fastff3.model.Account;
import com.github.ypiel.fastff3.model.Category;
import javafx.collections.ObservableList;

public class CategoryStringConverter extends IdLabelStringConverter<Category> {

    public CategoryStringConverter(ObservableList<Category> observableList) {
        super(observableList);
    }

}
