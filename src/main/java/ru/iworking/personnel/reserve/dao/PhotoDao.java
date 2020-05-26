package ru.iworking.personnel.reserve.dao;

import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.entity.Photo;

import java.util.List;

/**
 * @Override
 *     public LoadingCache<Long, Photo> initLoadingCache() {
 *         return CacheBuilder.newBuilder()
 *                 .maximumSize(1000)
 *                 .expireAfterWrite(60, TimeUnit.MINUTES)
 *                 .build(new CacheLoader<Long, Photo>() {
 *                     @Override
 *                     public Photo load(Long key) throws Exception {
 *                         return PhotoDao.getInstance().find(key);
 *                     }
 *                 });
 *     }
 */
@Component
public class PhotoDao extends Dao<Photo, Long> {

    @Override
    public Photo findById(Long id) {
        Photo entity = (Photo) getSessionProvider().getCurrentSession().get(Photo.class, id);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Photo> findAll() {
        List<Photo> entities = (List<Photo>) getSessionProvider().getCurrentSession().createQuery("from Photo").list();
        return entities;
    }

}
