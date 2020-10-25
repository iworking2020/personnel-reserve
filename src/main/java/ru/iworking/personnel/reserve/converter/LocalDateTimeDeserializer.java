package ru.iworking.personnel.reserve.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalDateTimeDeserializer extends StdConverter<String, LocalDateTime> {

    /**
     * Паттерн для:
     * 2020-10-01T06:51:51
     */
    private static final Pattern PATTERN_1 = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public LocalDateTime convert(String value) {
        Matcher m1 = PATTERN_1.matcher(value);
        if(m1.matches()) {
            return DATE_TIME_FORMATTER_1.parseDateTime(value).toLocalDateTime();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
