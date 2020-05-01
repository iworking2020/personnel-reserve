package ru.iworking.personnel.reserve.dao;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.Currency;
import ru.iworking.personnel.reserve.utils.db.HibernateUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CurrencyDao extends СachedDao<Currency, Long> {

    private static volatile CurrencyDao instance;

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

    @Override
    public LoadingCache<Long, Currency> initLoadingCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build(new CacheLoader<Long, Currency>() {
                    @Override
                    public Currency load(Long key) throws Exception {
                        return CurrencyDao.getInstance().find(key);
                    }
                });
    }

    @Override
    public void initCacheData(LoadingCache<Long, Currency> cash) {
        cash.putAll(findAll().stream().collect(Collectors.toMap(Currency::getId, Function.identity())));
    }

    @Override
    public List<Currency> findAll() {
        List<Currency> list;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        list = session.createQuery("FROM Currency", Currency.class).getResultList();
        session.flush();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public Currency find(Long id) {
        Currency currency = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        currency = session.get(Currency.class, id);
        session.flush();
        transaction.commit();
        session.close();
        return currency;
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


}
