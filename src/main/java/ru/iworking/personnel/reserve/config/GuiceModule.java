package ru.iworking.personnel.reserve.config;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import ru.iworking.personnel.reserve.annotation.GuiceComponent;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class GuiceModule extends AbstractModule {

    private static final Logger logger = LogManager.getLogger(GuiceModule.class);

    private void init() {
        Properties prop = new Properties();
        try {
            prop.load(GuiceModule.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException ex) {
            logger.error("application.properties not found...", ex);
        }

        String arrayPackages = prop.getProperty("guice.packages.scan");
        if (!Strings.isNullOrEmpty(arrayPackages)) {
            List<String> listPackages = Splitter.on(",")
                    .trimResults()
                    .omitEmptyStrings()
                    .splitToList(arrayPackages);

            listPackages.stream().forEach(packageComponents -> {
                Reflections reflections = new Reflections(packageComponents, new SubTypesScanner(false));

                Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);
                allClasses.forEach( entityObject -> {
                    Annotation[] annotations = entityObject.getAnnotations();
                    for (int i = 0; i < annotations.length; i++) {
                        if (annotations[i] instanceof GuiceComponent) {
                            addComponent((Class<Object>) entityObject);
                            break;
                        }
                    }
                } );
            });
        }
    }

    private void addComponent(Class<Object> clazz) {
        bind(clazz)
                .annotatedWith(Names.named(clazz.getName()))
                .to(clazz);
    }

    @Override
    protected void configure() {
        init();
    }

}
