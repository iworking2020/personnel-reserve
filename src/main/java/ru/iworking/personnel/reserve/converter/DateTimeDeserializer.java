package ru.iworking.personnel.reserve.converter;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeDeserializer extends StdConverter<String, DateTime> {

    /**
     * Паттерн для:
     * 2020-10-01T06:51:51-01:00
     * 2020-10-01T06:51:51+01:00
     * 2020-10-01T06:51:51Z
     */
    private static final Pattern PATTERN_1 = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}((Z)|((\\+|-)\\d{2}:\\d{2}))$");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    /**
     * Паттерн для:
     * 2020-10-01T06:51:51.500-01:00
     * 2020-10-01T06:51:51.500+01:00
     * 2020-10-01T06:51:51.500Z
     */
    private static final Pattern PATTERN_2 = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}((Z)|((\\+|-)\\d{2}:\\d{2}))$");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_2 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    /**
     * Паттерн для:
     * 2020-10-01-01:00
     * 2020-10-01+01:00
     * 2020-10-01Z
     */
    private static final Pattern PATTERN_3 = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}((Z)|((\\+|-)\\d{2}:\\d{2}))$");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_3 = DateTimeFormat.forPattern("yyyy-MM-ddZ");

    /**
     * Паттерн для:
     * 2020-10-01T06:51:51-0100
     * 2020-10-01T06:51:51+0100
     * 2020-10-01T06:51:51Z
     */
    private static final Pattern PATTERN_4 = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}((Z)|((\\+|-)\\d{2}\\d{2}))$");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_4 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    /**
     * Паттерн для:
     * 2020-10-01T06:51:51.500-0100
     * 2020-10-01T06:51:51.500+0100
     * 2020-10-01T06:51:51.500Z
     */
    private static final Pattern PATTERN_5 = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}((Z)|((\\+|-)\\d{2}\\d{2}))$");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_5 = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    /**
     * Паттерн для:
     * 2020-10-01-0100
     * 2020-10-01+0100
     * 2020-10-01Z
     */
    private static final Pattern PATTERN_6 = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}((Z)|((\\+|-)\\d{2}\\d{2}))$");
    private static final DateTimeFormatter DATE_TIME_FORMATTER_6 = DateTimeFormat.forPattern("yyyy-MM-ddZ");

    @Override
    public DateTime convert(String value) {
        Matcher m1 = PATTERN_1.matcher(value);
        Matcher m2 = PATTERN_2.matcher(value);
        Matcher m3 = PATTERN_3.matcher(value);
        Matcher m4 = PATTERN_4.matcher(value);
        Matcher m5 = PATTERN_5.matcher(value);
        Matcher m6 = PATTERN_6.matcher(value);
        if (m1.matches()) {
            return DATE_TIME_FORMATTER_1.parseDateTime(value);
        } else
        if (m2.matches()) {
            return DATE_TIME_FORMATTER_2.parseDateTime(value);
        } else
        if(m3.matches()) {
            return DATE_TIME_FORMATTER_3.parseDateTime(value);
        } else
        if(m4.matches()) {
            return DATE_TIME_FORMATTER_4.parseDateTime(value);
        } else
        if(m5.matches()) {
            return DATE_TIME_FORMATTER_5.parseDateTime(value);
        } else
        if(m6.matches()) {
            return DATE_TIME_FORMATTER_6.parseDateTime(value);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
