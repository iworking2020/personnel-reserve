package ru.iworking.personnel.reserve.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalDateDeserializer extends StdConverter<String, LocalDate> {

    /**
     * Паттерн для:
     * 2020-10-01
     */
    private static final Pattern PATTERN_1 = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final DateTimeFormatter DATE_FORMATTER_1 = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public LocalDate convert(String value) {
        Matcher m1 = PATTERN_1.matcher(value);
        if(m1.matches()) {
            return DATE_FORMATTER_1.parseDateTime(value).toLocalDate();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
