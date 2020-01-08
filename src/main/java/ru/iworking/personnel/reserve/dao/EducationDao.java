package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.Education;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.List;

public class EducationDao implements Dao<Education, Long> {

    private static volatile EducationDao instance;

    @Override
    public List<Education> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Education> list = session.createQuery("FROM Education", Education.class).list();
        session.flush();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public Education find(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Education profField = session.get(Education.class, id);
        session.flush();
        transaction.commit();
        session.close();
        return profField;
    }

    @Override
    public Education create(Education obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public Education update(Education obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public void delete(Education obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        session.flush();
        transaction.commit();
        session.close();
    }

    public static EducationDao getInstance() {
        EducationDao localInstance = instance;
        if (localInstance == null) {
            synchronized (EducationDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new EducationDao();
                }
            }
        }
        return localInstance;
    }
}
