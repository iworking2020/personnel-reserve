package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.ProfField;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.List;

public class ProfFieldDao implements Dao<ProfField, Long> {

    private static volatile ProfFieldDao instance;

    @Override
    public List<ProfField> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<ProfField> list = session.createQuery("FROM ProfField", ProfField.class).list();
        session.flush();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public ProfField find(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        ProfField profField = session.get(ProfField.class, id);
        session.flush();
        transaction.commit();
        session.close();
        return profField;
    }

    @Override
    public ProfField create(ProfField obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public ProfField update(ProfField obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public void delete(ProfField obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        session.flush();
        transaction.commit();
        session.close();
    }

    public static ProfFieldDao getInstance() {
        ProfFieldDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ProfFieldDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ProfFieldDao();
                }
            }
        }
        return localInstance;
    }
}
