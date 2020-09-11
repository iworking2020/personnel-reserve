package ru.iworking.personnel.reserve.dao;

import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.entity.ImageContainer;

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
public class ImageContainerDao extends Dao<ImageContainer, Long> {

    @Override
    public ImageContainer findById(Long id) {
        ImageContainer entity = (ImageContainer) getSessionProvider().getCurrentSession().get(ImageContainer.class, id);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ImageContainer> findAll() {
        List<ImageContainer> entities = (List<ImageContainer>) getSessionProvider().getCurrentSession().createQuery("from Photo").list();
        return entities;
    }

}
