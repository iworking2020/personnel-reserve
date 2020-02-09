package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.Photo;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.List;

public class PhotoDao implements Dao<Photo, Long> {

    private static volatile PhotoDao instance;

    @Override
    public List<Photo> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Photo> list = session.createQuery("FROM Photo", Photo.class).list();
        session.flush();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public Photo find(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Photo photo = session.get(Photo.class, id);
        session.flush();
        transaction.commit();
        session.close();
        return photo;
    }

    @Override
    public Photo create(Photo obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public Photo update(Photo obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        session.flush();
        transaction.commit();
        session.close();
        return obj;
    }

    @Override
    public void delete(Photo obj) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        session.flush();
        transaction.commit();
        session.close();
    }

    public static PhotoDao getInstance() {
        PhotoDao localInstance = instance;
        if (localInstance == null) {
            synchronized (PhotoDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PhotoDao();
                }
            }
        }
        return localInstance;
    }
}
