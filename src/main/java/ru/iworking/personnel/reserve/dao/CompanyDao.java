package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class CompanyDao implements Dao<Company, Long> {

    private static volatile CompanyDao instance;

    public Long count(Company company) {
        Long count = 0L;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("select COUNT(company) from Company as company where id = :id", Long.class);
            query.setParameter("id", company.getId());
            count = (Long) query.getSingleResult();
            session.flush();
            transaction.commit();
        }
        return count;
    }

    @Override
    public List<Company> findAll() {
        List<Company> list;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            list = session.createQuery("FROM Company", Company.class).list();
            session.flush();
            transaction.commit();
        }
        return list;
    }
    
    public List<Company> findAll(Map<String, Object> params) {
        String sql = "from Company as company";
        List<Company> list;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery(sql, Company.class);
            list = query.getResultList();
            session.flush();
            transaction.commit();
        }
        return list;
    }

    @Override
    public Company find(Long id) {
        Company company;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(Company.class, id);
            session.flush();
            transaction.commit();
        }
        return company;
    }

    @Override
    public Company create(Company company) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(company);
            session.flush();
            transaction.commit();
        }
        return company;
    }

    @Override
    public Company update(Company obj) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(obj);
            session.flush();
            transaction.commit();
        }
        return obj;
    }

    @Override
    public void delete(Company company) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(company);
            session.flush();
            transaction.commit();
        }
    }

    public static CompanyDao getInstance() {
        CompanyDao localInstance = instance;
        if (localInstance == null) {
            synchronized (CompanyDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CompanyDao();
                }
            }
        }
        return localInstance;
    }

}
