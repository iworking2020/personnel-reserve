package ru.iworking.personnel.reserve.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LocalDateSerializer extends StdConverter<LocalDate, String> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public String convert(LocalDate localDate) {
        return localDate.toString(DATE_TIME_FORMATTER);
    }
}
