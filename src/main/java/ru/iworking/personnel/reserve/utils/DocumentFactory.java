package ru.iworking.personnel.reserve.utils;

import java.util.Map;

public abstract class DocumentFactory {

    public abstract Document create(Map<String, Object> props);

}
