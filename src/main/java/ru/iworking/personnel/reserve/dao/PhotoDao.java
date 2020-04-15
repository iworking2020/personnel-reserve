package ru.iworking.personnel.reserve.dao;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.Photo;
import ru.iworking.personnel.reserve.utils.HibernateUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PhotoDao extends CashedDao<Photo, Long> {

    private static volatile PhotoDao instance;

    @Override
    public LoadingCache<Long, Photo> initLoadingCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build(new CacheLoader<Long, Photo>() {
                    @Override
                    public Photo load(Long key) throws Exception {
                        return PhotoDao.getInstance().find(key);
                    }
                });
    }

    @Override
    public void initCashData(LoadingCache<Long, Photo> cash) {
        //cash.putAll(findAll().stream().collect(Collectors.toMap(Photo::getId, Function.identity())));
    }

    public Photo createAndUpdateInCash(Photo photo) {
        Photo photo1 = create(photo);
        updateInCash(photo1.getId());
        return photo1;
    }

    public Photo updateAndUpdateInCash(Photo photo) {
        Photo photo1 = update(photo);
        updateInCash(photo1.getId());
        return photo1;
    }

    public void deleteAndRemoveFromCash(Photo photo) {
        delete(photo);
        removeFromCash(photo.getId());
    }

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
