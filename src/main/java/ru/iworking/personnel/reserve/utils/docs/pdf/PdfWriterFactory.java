package ru.iworking.personnel.reserve.utils.docs.pdf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.iworking.personnel.reserve.utils.docs.DocumentWriterFactory;

import java.util.Map;

public abstract class PdfWriterFactory implements DocumentWriterFactory {

    private static final Logger logger = LogManager.getLogger(PdfWriterFactory.class);

    public abstract void write(Map<String, Object> props);

}
