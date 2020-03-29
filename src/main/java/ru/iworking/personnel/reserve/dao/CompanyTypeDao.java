package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.CompanyType;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompanyTypeDao implements Dao<CompanyType, Long> {

    private static volatile CompanyTypeDao instance;

    private Map<Long, CompanyType> cashMap = null;

    @Override
    public List<CompanyType> findAll() {
        if (cashMap == null || cashMap.isEmpty()) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            cashMap = session.createQuery("FROM CompanyType", CompanyType.class).list()
                    .stream().collect(Collectors.toMap(CompanyType::getId, Function.identity()));
            session.flush();
            transaction.commit();
            session.close();
        }
        return cashMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public CompanyType find(Long id) {
        if (cashMap == null) cashMap = new LinkedHashMap<>();
        if (!cashMap.containsKey(id)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            cashMap.put(id, session.get(CompanyType.class, id));
            session.flush();
            transaction.commit();
            session.close();
        }
        return cashMap.get(id);
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
