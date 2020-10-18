package ru.iworking.personnel.reserve.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.entity.Profile;

@Component
public class ProfileFieldSetMapper implements FieldSetMapper<Profile> {
    @Override
    public Profile mapFieldSet(FieldSet fieldSet) {
        final Profile profile = new Profile();
        profile.setFirstName(fieldSet.readString("firstName"));
        profile.setLastName(fieldSet.readString("lastName"));
        profile.setMiddleName(fieldSet.readString("middleName"));
        /*voltage.setVolt(fieldSet.readBigDecimal("volt"));
        voltage.setTime(fieldSet.readDouble("time"));*/
        return profile;
    }
}
