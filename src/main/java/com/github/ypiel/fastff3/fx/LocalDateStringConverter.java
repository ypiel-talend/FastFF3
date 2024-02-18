package com.github.ypiel.fastff3.fx;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateStringConverter extends StringConverter<LocalDate> {

    private final DateTimeFormatter str2date;
    private final DateTimeFormatter date2str;

    public LocalDateStringConverter(DateTimeFormatter date2str,DateTimeFormatter str2date){
        this.str2date = str2date;
        this.date2str = date2str;
    }

    @Override
    public String toString(LocalDate localDate) {
        return localDate.format(date2str);
    }

    @Override
    public LocalDate fromString(String s) {
        return LocalDate.parse(s, str2date);
    }
}
