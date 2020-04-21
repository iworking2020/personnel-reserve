package ru.iworking.personnel.reserve.utils.docs.exel;

import ru.iworking.personnel.reserve.utils.docs.DocumentWriterFactory;

import java.util.Map;

public abstract class ExelWriterFactory implements DocumentWriterFactory {

    public abstract void write(Map<String, Object> props);

}
