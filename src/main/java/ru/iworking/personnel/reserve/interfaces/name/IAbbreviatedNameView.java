package ru.iworking.personnel.reserve.interfaces.name;

import java.util.Locale;
import java.util.Map;

public interface IAbbreviatedNameView extends IName {

    String getName(Locale locale);

    Map<Locale, String> getNames();

}
