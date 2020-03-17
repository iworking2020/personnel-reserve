package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.Education;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EducationDao implements Dao<Education, Long> {

    private static volatile EducationDao instance;

    private Map<Long, Education> cashMap = null;

    @Override
    public List<Education> findAll() {
        if (cashMap == null || cashMap.isEmpty()) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            cashMap = session.createQuery("FROM Education", Education.class).list()
                    .stream().collect(Collectors.toMap(Education::getId, Function.identity()));
            session.flush();
            transaction.commit();
            session.close();
        }
        return cashMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Education find(Long id) {
        if (cashMap == null) cashMap = new LinkedHashMap<>();
        if (!cashMap.containsKey(id)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            cashMap.put(id, session.get(Education.class, id));
            session.flush();
            transaction.commit();
            session.close();
        }
        return cashMap.get(id);
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
