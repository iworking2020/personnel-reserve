package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.WorkType;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.List;

public class WorkTypeDao implements Dao<WorkType, Long> {

    private static volatile WorkTypeDao instance;

    @Override
    public List<WorkType> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<WorkType> list = session.createQuery("FROM WorkType", WorkType.class).list();
        session.flush();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public WorkType find(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        WorkType profField = session.get(WorkType.class, id);
        session.flush();
        transaction.commit();
        session.close();
        return profField;
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
