package ru.iworking.personnel.reserve.utils;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import ru.iworking.personnel.reserve.converter.LocalDateAttributeConverter;
import ru.iworking.personnel.reserve.converter.LocalDateTimeAttributeConverter;
import ru.iworking.personnel.reserve.entity.*;

import javax.persistence.Query;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

public class HibernateUtil {

    static final Logger logger = LogManager.getLogger(HibernateUtil.class);

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
                settings.put(Environment.DRIVER, "org.h2.Driver");
                settings.put(Environment.URL, "jdbc:h2:" + DirProps.PROJECT_DIR + File.separator + "database");
                settings.put(Environment.USER, "sa");
                settings.put(Environment.PASS, "");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.ENABLE_LAZY_LOAD_NO_TRANS, "true");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.FORMAT_SQL, "true");
                settings.put(Environment.POOL_SIZE, 1);
                settings.put(Environment.HBM2DDL_AUTO, "update");

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

                HibernateUtil.initData(sessionFactory);
            } catch (Exception e) {
                logger.error(e);
            }
        }
        return sessionFactory;
    }

    private static void initData(SessionFactory sessionFactory) throws IOException, URISyntaxException {
        InputStream inputData = HibernateUtil.class.getClassLoader().getResourceAsStream("data-h2.sql");
        if (inputData != null) {
            String sql = IOUtils.toString(inputData, "UTF-8");

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
