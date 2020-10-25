package ru.iworking.personnel.reserve.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends StdConverter<LocalDateTime, String> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public String convert(LocalDateTime localDateTime) {
        return localDateTime.toString(DATE_TIME_FORMATTER);
    }
}
