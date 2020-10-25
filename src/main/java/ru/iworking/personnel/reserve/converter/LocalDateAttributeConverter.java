package ru.iworking.personnel.reserve.converter;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {

    public static final DateTimeZone jodaTzUTC = DateTimeZone.getDefault();

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        if (localDate == null) {
            return null;
        } else {
            return new Date(localDate.toDateTimeAtStartOfDay(jodaTzUTC).getMillis());
        }
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        if (date == null) {
            return null;
        } else {
            return new LocalDate(date.getTime(), jodaTzUTC);
        }
    }

}
