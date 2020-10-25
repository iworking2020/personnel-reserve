package ru.iworking.personnel.reserve.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimeUtil {

    public static Integer calAge(org.joda.time.LocalDate birthDate, org.joda.time.LocalDate currentDate) {
        if (birthDate == null) return null;
        if (currentDate == null) currentDate = org.joda.time.LocalDate.now();
        return org.joda.time.Years.yearsBetween(birthDate, currentDate).getYears();
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

}
