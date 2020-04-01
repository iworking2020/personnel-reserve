package ru.iworking.personnel.reserve.dao;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.ProfField;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProfFieldDao extends CashedDao<ProfField, Long> {

    private static volatile ProfFieldDao instance;

    @Override
    public LoadingCache<Long, ProfField> initLoadingCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build(new CacheLoader<Long, ProfField>() {
                    @Override
                    public ProfField load(Long key) throws Exception {
                        return ProfFieldDao.getInstance().find(key);
                    }
                });
    }

    @Override
    public void initCashData(LoadingCache<Long, ProfField> cash) {
        cash.putAll(findAll().stream().collect(Collectors.toMap(ProfField::getId, Function.identity())));
    }

    @Override
    public List<ProfField> findAll() {
        List<ProfField> list;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        list = session.createQuery("FROM ProfField", ProfField.class).getResultList();
        session.flush();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public ProfField find(Long id) {
        ProfField profField = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        profField = session.get(ProfField.class, id);
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
