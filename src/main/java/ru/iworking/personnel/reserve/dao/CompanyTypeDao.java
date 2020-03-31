package ru.iworking.personnel.reserve.dao;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.CompanyType;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompanyTypeDao extends CashedDao<CompanyType, Long> {

    private static final Logger logger = LogManager.getLogger(CompanyTypeDao.class);

    private static volatile CompanyTypeDao instance;

    @Override
    public LoadingCache<Long, CompanyType> initLoadingCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build(new CacheLoader<Long, CompanyType>() {
                    @Override
                    public CompanyType load(Long key) throws Exception {
                        return CompanyTypeDao.getInstance().find(key);
                    }
                });
    }

    @Override
    public void initCashData(LoadingCache<Long, CompanyType> cash) {
        cash.putAll(findAll().stream()
                .collect(Collectors.toMap(CompanyType::getId, Function.identity()))
        );
    }

    @Override
    public List<CompanyType> findAll() {
        List<CompanyType> list = new LinkedList<>();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        list = session.createQuery("FROM CompanyType", CompanyType.class).getResultList();
        session.flush();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public CompanyType find(Long id) {
        CompanyType companyType;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        companyType = session.get(CompanyType.class, id);
        session.flush();
        transaction.commit();
        session.close();
        return companyType;
    }

    @Override
    public CompanyType create(CompanyType obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public CompanyType update(CompanyType obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public void delete(CompanyType obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        session.flush();
        transaction.commit();
        session.close();
    }

    public static CompanyTypeDao getInstance() {
        CompanyTypeDao localInstance = instance;
        if (localInstance == null) {
            synchronized (CompanyTypeDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CompanyTypeDao();
                }
            }
        }
        return localInstance;
    }
}
