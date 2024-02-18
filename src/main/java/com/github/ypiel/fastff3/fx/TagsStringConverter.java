package com.github.ypiel.fastff3.fx;

import com.github.ypiel.fastff3.model.IdLabel;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TagsStringConverter extends StringConverter<List<String>> {


    @Override
    public String toString(List<String> tags) {
        return tags.stream().collect(Collectors.joining(" "));
    }

    @Override
    public List<String> fromString(String s) {
        List<String> tags = Arrays.asList(Optional.ofNullable(s).orElse("").split(" "));
        return tags;
    }
}
