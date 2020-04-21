package ru.iworking.personnel.reserve.utils.docs;

import java.util.Map;

public interface DocumentWriterFactory {

    void write(Map<String, Object> props);

}
