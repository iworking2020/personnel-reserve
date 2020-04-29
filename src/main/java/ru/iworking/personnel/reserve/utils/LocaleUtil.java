package ru.iworking.personnel.reserve.utils;

import java.util.Locale;

public class LocaleUtil {
    public static final Locale LOCALE_RU = new Locale("ru", "RU");
    public static final Locale LOCALE_EN;

    public LocaleUtil() {
    }

    public static Locale getDefault() {
        return LOCALE_RU;
    }

    public static Locale[] getAll() {
        return new Locale[]{LOCALE_RU, LOCALE_EN};
    }

    static {
        LOCALE_EN = Locale.ENGLISH;
    }
}
