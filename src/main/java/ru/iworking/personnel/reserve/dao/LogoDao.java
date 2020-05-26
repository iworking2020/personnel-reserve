package ru.iworking.personnel.reserve.dao;

import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.entity.Logo;

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
public class LogoDao extends Dao<Logo, Long> {

    @Override
    public Logo findById(Long id) {
        Logo entity = (Logo) getSessionProvider().getCurrentSession().get(Logo.class, id);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Logo> findAll() {
        List<Logo> entities = (List<Logo>) getSessionProvider().getCurrentSession().createQuery("from Logo").list();
        return entities;
    }

}
