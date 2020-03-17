package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.Currency;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CurrencyDao implements Dao<Currency, Long> {

    private static volatile CurrencyDao instance;

    private Map<Long, Currency> cashMap = null;

    @Override
    public List<Currency> findAll() {
        if (cashMap == null || cashMap.isEmpty()) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            cashMap = session.createQuery("FROM Currency", Currency.class).list()
                    .stream().collect(Collectors.toMap(Currency::getId, Function.identity()));
            session.flush();
            transaction.commit();
            session.close();
        }
        return cashMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Currency find(Long id) {
        if (cashMap == null) cashMap = new LinkedHashMap<>();
        if (!cashMap.containsKey(id)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            cashMap.put(id, session.get(Currency.class, id));
            session.flush();
            transaction.commit();
            session.close();
        }
        return cashMap.get(id);
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
