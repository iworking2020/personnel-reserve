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
import ru.iworking.personnel.reserve.converter.LocalDateAttributeConverter;
import ru.iworking.personnel.reserve.converter.LocalDateTimeAttributeConverter;
import ru.iworking.personnel.reserve.entity.*;

import javax.persistence.Query;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

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

                configuration.addAnnotatedClass(Resume.class);
                configuration.addAnnotatedClass(ProfField.class);
                configuration.addAnnotatedClass(WorkType.class);
                configuration.addAnnotatedClass(Education.class);
                configuration.addAnnotatedClass(Experience.class);
                configuration.addAnnotatedClass(Currency.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                try {
                    HibernateUtil.initData(sessionFactory);
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

    private static void initData(SessionFactory sessionFactory) throws Exception {
        String encoding = settings.getProperty("hibernate.init.file.encoding");
        String platform = settings.getProperty("hibernate.database.platform");

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
