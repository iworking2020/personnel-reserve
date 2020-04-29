package ru.iworking.personnel.reserve.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public class TimeUtil {

    public static Integer calAge(LocalDate birthDate, LocalDate currentDate) {
        if (birthDate == null) return null;
        if (currentDate == null) currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }

    public static Integer calAge(java.util.Date birthDate, java.util.Date currentDate) {
        if (birthDate == null) return null;
        if (currentDate == null) currentDate = new java.util.Date();
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(birthDate));
        int d2 = Integer.parseInt(formatter.format(currentDate));
        int age = (d2 - d1) / 10000;
        return age;
    }

    public static LocalDate convertToLocalDate(java.util.Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
