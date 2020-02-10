package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.ProfField;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProfFieldDao implements Dao<ProfField, Long> {

    private static volatile ProfFieldDao instance;

    private Map<Long, ProfField> cashMap = null;

    @Override
    public List<ProfField> findAll() {
        if (cashMap == null || cashMap.isEmpty()) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            cashMap = session.createQuery("FROM ProfField", ProfField.class).list()
                    .stream().collect(Collectors.toMap(ProfField::getId, Function.identity()));
            session.flush();
            transaction.commit();
            session.close();
        }
        return cashMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public ProfField find(Long id) {
        if (cashMap == null) cashMap = new LinkedHashMap<>();
        if (!cashMap.containsKey(id)) {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            cashMap.put(id, session.get(ProfField.class, id));
            session.flush();
            transaction.commit();
            session.close();
        }
        return cashMap.get(id);
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
