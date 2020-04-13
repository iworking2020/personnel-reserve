package ru.iworking.personnel.reserve.dao;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.ResumeState;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResumeStateDao extends CashedDao<ResumeState, Long> {

    private static volatile ResumeStateDao instance;
    
    @Override
    public LoadingCache<Long, ResumeState> initLoadingCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build(new CacheLoader<Long, ResumeState>() {
                    @Override
                    public ResumeState load(Long key) throws Exception {
                        return ResumeStateDao.getInstance().find(key);
                    }
                });
    }

    @Override
    public void initCashData(LoadingCache<Long, ResumeState> cash) {
        cash.putAll(findAll().stream().collect(Collectors.toMap(ResumeState::getId, Function.identity())));
    }

    @Override
    public List<ResumeState> findAll() {
        List<ResumeState> list;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        list = session.createQuery("FROM ResumeState", ResumeState.class).getResultList();
        session.flush();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public ResumeState find(Long id) {
        ResumeState resumeState = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        resumeState = session.get(ResumeState.class, id);
        session.flush();
        transaction.commit();
        session.close();
        return resumeState;
    }

    @Override
    public ResumeState create(ResumeState obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public ResumeState update(ResumeState obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public void delete(ResumeState obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        session.flush();
        transaction.commit();
        session.close();
    }

    public static ResumeStateDao getInstance() {
        ResumeStateDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ResumeStateDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ResumeStateDao();
                }
            }
        }
        return localInstance;
    }
    
}
