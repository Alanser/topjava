package ru.javawebinar.topjava.web.formatter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {

    @Override
    public String print(LocalDate date, Locale locale) {
        return date == null ? "" : date.toString();
    }

    @Override
    public LocalDate parse(String text, Locale locale) {
        return text != null && !text.isEmpty() ? LocalDate.parse(text) : null;
    }
}
