package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.WorkType;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WorkTypeDao implements Dao<WorkType, Long> {

    private static volatile WorkTypeDao instance;

    private Map<Long, WorkType> cashMap = null;

    @Override
    public List<WorkType> findAll() {
        if (cashMap == null || cashMap.isEmpty()) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            cashMap = session.createQuery("FROM WorkType", WorkType.class).list()
                    .stream().collect(Collectors.toMap(WorkType::getId, Function.identity()));
            session.flush();
            transaction.commit();
            session.close();
        }
        return cashMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public WorkType find(Long id) {
        if (cashMap == null) cashMap = new LinkedHashMap<>();
        if (!cashMap.containsKey(id)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            cashMap.put(id, session.get(WorkType.class, id));
            session.flush();
            transaction.commit();
            session.close();
        }
        return cashMap.get(id);
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
