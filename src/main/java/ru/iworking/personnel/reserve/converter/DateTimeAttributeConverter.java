package ru.iworking.personnel.reserve.converter;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.util.Objects;

@Converter(autoApply = true)
public class DateTimeAttributeConverter implements AttributeConverter<DateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(DateTime dateTime) {
        return Objects.isNull(dateTime) ? null : new Timestamp(dateTime.getMillis());
    }

    @Override
    public DateTime convertToEntityAttribute(Timestamp timestamp) {
        return Objects.isNull(timestamp) ? null : new DateTime(timestamp, DateTimeZone.getDefault());
    }

}
