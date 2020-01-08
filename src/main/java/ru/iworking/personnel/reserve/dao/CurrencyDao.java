package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.Currency;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.List;

public class CurrencyDao implements Dao<Currency, Long> {

    private static volatile CurrencyDao instance;

    @Override
    public List<Currency> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Currency> list = session.createQuery("FROM Currency", Currency.class).list();
        session.flush();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public Currency find(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Currency obj = session.get(Currency.class, id);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public Currency create(Currency obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public Currency update(Currency obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public void delete(Currency obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        session.flush();
        transaction.commit();
        session.close();
    }

    public static CurrencyDao getInstance() {
        CurrencyDao localInstance = instance;
        if (localInstance == null) {
            synchronized (CurrencyDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CurrencyDao();
                }
            }
        }
        return localInstance;
    }
}
