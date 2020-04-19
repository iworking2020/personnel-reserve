package ru.iworking.personnel.reserve.utils;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import ru.iworking.personnel.reserve.converter.LocalDateAttributeConverter;
import ru.iworking.personnel.reserve.converter.LocalDateTimeAttributeConverter;

import javax.persistence.Query;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Properties;
import java.util.Set;

public class HibernateUtil {

    static final Logger logger = LogManager.getLogger(HibernateUtil.class);

    private static SessionFactory sessionFactory;
    private static Properties settings;

    public static Properties getProperties() {
        if (settings == null) HibernateUtil.getSessionFactory();
        return settings;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                settings = new Properties();
                try {
                    settings.load(HibernateUtil.class.getClassLoader().getResourceAsStream("hibernate.properties"));
                } catch (FileNotFoundException ex) {
                    logger.error("hibernate.properties not found...", ex);
                }
                configuration.setProperties(settings);

                configuration.addAttributeConverter(LocalDateAttributeConverter.class);
                configuration.addAttributeConverter(LocalDateTimeAttributeConverter.class);

                loadEntityClasses(configuration, settings);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                try {
                    initData(sessionFactory, settings);
                } catch (Exception ex) {
                    logger.error("Init file not found... ");
                }

            } catch (Exception e) {
                logger.error(e);
            }
        }
        return sessionFactory;
    }

    public static void shutDown(){
        getSessionFactory().close();
        sessionFactory = null;
    }

    private static void loadEntityClasses(Configuration configuration, Properties properties) {
        String namePackageToScan = properties.getProperty("hibernate.init.package.scan");

        Reflections reflections = new Reflections(namePackageToScan, new SubTypesScanner(false));

        Set<Class<? extends Object>> allClasses = reflections.getSubTypesOf(Object.class);
        allClasses.forEach( entityObject -> {
            Annotation[] annotations = entityObject.getAnnotations();
            for (int i = 0; i < annotations.length; i++) {
                if (annotations[i] instanceof javax.persistence.Entity) {
                    configuration.addAnnotatedClass(entityObject);
                    break;
                }
            }
        } );
    }

    private static void initData(SessionFactory sessionFactory, Properties properties) throws Exception {
        String encoding = properties.getProperty("hibernate.init.file.encoding");
        String platform = properties.getProperty("hibernate.database.platform");

        String nameSqlFile = platform != null ? "data-"+platform+".sql" : "data.sql";

        InputStream inputData = HibernateUtil.class.getClassLoader().getResourceAsStream(nameSqlFile);
        if (inputData != null) {
            String sql = IOUtils.toString(inputData, encoding);

            Session session = sessionFactory.getCurrentSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createNativeQuery(sql);
            query.executeUpdate();
            session.flush();
            transaction.commit();
        } else {
            logger.warn("File for data install not found...");
        }
    }

}
