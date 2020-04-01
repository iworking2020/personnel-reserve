package ru.iworking.personnel.reserve.dao;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.WorkType;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WorkTypeDao extends CashedDao<WorkType, Long> {

    private static volatile WorkTypeDao instance;

    @Override
    public LoadingCache<Long, WorkType> initLoadingCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build(new CacheLoader<Long, WorkType>() {
                    @Override
                    public WorkType load(Long key) throws Exception {
                        return WorkTypeDao.getInstance().find(key);
                    }
                });
    }

    @Override
    public void initCashData(LoadingCache<Long, WorkType> cash) {
        cash.putAll(findAll().stream().collect(Collectors.toMap(WorkType::getId, Function.identity())));
    }

    @Override
    public List<WorkType> findAll() {
        List<WorkType> list;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        list = session.createQuery("FROM WorkType", WorkType.class).getResultList();
        session.flush();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public WorkType find(Long id) {
        WorkType workType = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        workType = session.get(WorkType.class, id);
        session.flush();
        transaction.commit();
        session.close();
        return workType;
    }

    @Override
    public WorkType create(WorkType obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public WorkType update(WorkType obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public void delete(WorkType obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        session.flush();
        transaction.commit();
        session.close();
    }

    public static WorkTypeDao getInstance() {
        WorkTypeDao localInstance = instance;
        if (localInstance == null) {
            synchronized (WorkTypeDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new WorkTypeDao();
                }
            }
        }
        return localInstance;
    }
}
