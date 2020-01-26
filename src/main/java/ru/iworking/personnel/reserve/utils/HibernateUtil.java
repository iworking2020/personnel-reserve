package ru.iworking.personnel.reserve.utils;

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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class HibernateUtil {

    static final Logger logger = LogManager.getLogger(HibernateUtil.class);

    private static String urlHibernateProperties = "./data/hibernate.properties";
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                try {
                    settings.load(new FileInputStream(urlHibernateProperties));
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
                    HibernateUtil.initData(sessionFactory,
                            settings.getProperty("hibernate.init.file.url"),
                            settings.getProperty("hibernate.init.file.encoding"));
                } catch (Exception ex) {
                    logger.error("Init file not found... ", ex);
                }

            } catch (Exception e) {
                logger.error(e);
            }
        }
        return sessionFactory;
    }

    private static void initData(SessionFactory sessionFactory, String url, String encoding) throws Exception {
        String sql = FileUtil.readFileAsString(url, encoding);

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createNativeQuery(sql);
        query.executeUpdate();
        session.flush();
        transaction.commit();
    }



}
