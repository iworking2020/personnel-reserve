package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.interfaces.Dao;
import ru.iworking.personnel.reserve.utils.db.HibernateUtil;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class VacancyDao implements Dao<Vacancy, Long> {

    private static volatile VacancyDao instance;

    public Long count(Vacancy vacancy) {
        Long count = 0L;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("select COUNT(vacancy) from Vacancy as vacancy where id = :id", Long.class);
            query.setParameter("id", vacancy.getId());
            count = (Long) query.getSingleResult();
            session.flush();
            transaction.commit();
        }
        return count;
    }

    @Override
    public List<Vacancy> findAll() {
        List<Vacancy> list;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            list = session.createQuery("FROM Vacancy", Vacancy.class).list();
            session.flush();
            transaction.commit();
        }
        return list;
    }

    public List<Vacancy> findAllByCompanyId(Long companyId) {
        List<Vacancy> list;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("FROM Vacancy as v where v.companyId = :companyId", Vacancy.class);
            query.setParameter("companyId", companyId);
            list = query.getResultList();
            session.flush();
            transaction.commit();
        }
        return list;
    }
    
    public List<Vacancy> findAll(Map<String, Object> params) {
        String sql = "from Vacancy as vacancy";
        List<Vacancy> list;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery(sql, Vacancy.class);
            list = query.getResultList();
            session.flush();
            transaction.commit();
        }
        return list;
    }

    @Override
    public Vacancy find(Long id) {
        Vacancy vacancy;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            vacancy = session.get(Vacancy.class, id);
            session.flush();
            transaction.commit();
        }
        return vacancy;
    }

    @Override
    public Vacancy create(Vacancy vacancy) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(vacancy);
            session.flush();
            transaction.commit();
        }
        return vacancy;
    }

    @Override
    public Vacancy update(Vacancy obj) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(obj);
            session.flush();
            transaction.commit();
        }
        return obj;
    }

    @Override
    public void delete(Vacancy vacancy) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(vacancy);
            session.flush();
            transaction.commit();
        }
    }

    public void deleteByCompanyId(Long companyId) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            session.createQuery("delete Vacancy as v where v.companyId = :companyId")
                    .setParameter("companyId", companyId)
                    .executeUpdate();

            session.flush();
            transaction.commit();
        }
    }

    public static VacancyDao getInstance() {
        VacancyDao localInstance = instance;
        if (localInstance == null) {
            synchronized (VacancyDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new VacancyDao();
                }
            }
        }
        return localInstance;
    }

}
