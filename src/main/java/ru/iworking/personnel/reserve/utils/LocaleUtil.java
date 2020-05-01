package ru.iworking.personnel.reserve.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public class LocaleUtil {

    private static final Logger logger = LogManager.getLogger(LocaleUtil.class);

    private static volatile LocaleUtil instance;
    public static LocaleUtil getInstance() {
        LocaleUtil localInstance = instance;
        if (localInstance == null) {
            synchronized (LocaleUtil.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new LocaleUtil();
                }
            }
        }
        return localInstance;
    }

    private final Set<Locale> locales = new LinkedHashSet<>();

    private Locale localeDefault;

    public static final Locale LOCALE_RU = new Locale("ru", "RU");

    private LocaleUtil() {
        locales.add(LOCALE_RU);
        locales.add(Locale.ENGLISH);
        locales.add(Locale.FRANCE);
        locales.add(Locale.GERMANY);
        locales.add(Locale.JAPAN);
        this.setDefault(getFirstElement());
        initData();
    }

    private void initData() {
        try (InputStream input = LocaleUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            String language = prop.getProperty("locale.default.language");
            logger.debug(String.format("loaded locale.default.language = %s", language));
            String country = prop.getProperty("locale.default.country");
            logger.debug(String.format("loaded locale.default.country = %s", country));

            if (language != null && !language.isEmpty()) {
                Locale newLocale = new Locale(language, country);
                locales.add(newLocale);
                this.setDefault(newLocale);
            }
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    public void setDefault(Locale locale) {
        localeDefault = locale;
    }
    public Locale getDefault() {
        return localeDefault;
    }

    public Object[] getAll() {
        return locales.toArray();
    }

    public Locale getFirstElement() {
        return (Locale) getAll()[0];
    }

}
