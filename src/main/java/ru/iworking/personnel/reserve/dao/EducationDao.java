package ru.iworking.personnel.reserve.dao;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.Education;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EducationDao extends CashedDao<Education, Long> {

    private static volatile EducationDao instance;

    @Override
    public LoadingCache<Long, Education> initLoadingCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build(new CacheLoader<Long, Education>() {
                    @Override
                    public Education load(Long key) throws Exception {
                        return EducationDao.getInstance().find(key);
                    }
                });
    }

    @Override
    public void initCashData(LoadingCache<Long, Education> cash) {
        cash.putAll(findAll().stream().collect(Collectors.toMap(Education::getId, Function.identity())));
    }

    @Override
    public List<Education> findAll() {
        List<Education> list;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        list = session.createQuery("FROM Education", Education.class).getResultList();
        session.flush();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public Education find(Long id) {
        Education education = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        education = session.get(Education.class, id);
        session.flush();
        transaction.commit();
        session.close();
        return education;
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
