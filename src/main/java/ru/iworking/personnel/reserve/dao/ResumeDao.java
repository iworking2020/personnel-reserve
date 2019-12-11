package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.ProfField;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

public class ResumeDao implements Dao<Resume, Long> {

    public List<Resume> findAllByProfField(ProfField profField) {
        List<Resume> list;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from Resume as resume where resume.profField = :profField", Resume.class);
            query.setParameter("profField", profField);
            list = query.getResultList();
            session.flush();
            transaction.commit();
        }
        return list;
    }

    @Override
    public List<Resume> findAll() {
        List<Resume> list;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            list = session.createQuery("FROM Resume", Resume.class).list();
            session.flush();
            transaction.commit();
        }
        return list;
    }

    @Override
    public Resume find(Long id) {
        Resume resume;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            resume = session.get(Resume.class, id);
            session.flush();
            transaction.commit();
        }
        return resume;
    }

    @Override
    public Resume create(Resume resume) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(resume);
            session.flush();
            transaction.commit();
        }
        return resume;
    }

    @Override
    public Resume update(Resume obj) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(obj);
            session.flush();
            transaction.commit();
        }
        return obj;
    }

    @Override
    public void delete(Resume resume) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(resume);
            session.flush();
            transaction.commit();
        }
    }

}
