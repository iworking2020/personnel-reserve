package ru.iworking.personnel.reserve.dao;

import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public abstract class Сached<O, I extends Serializable> {

    private static final Logger logger = LogManager.getLogger(Сached.class);

    private LoadingCache<I, O> cache;

    public Сached() {
        cache = initLoadingCache();
        initCacheData(cache);
    }

    public abstract LoadingCache<I, O> initLoadingCache();

    /**
     * Данный метод отвечает за заполнение хранилищя объектов
     * первоначальными данными.
     */
    public void initCacheData(LoadingCache<I, O> cache) {  }

    public O findFromCash(I id) {
        try {
            return cache.get(id);
        } catch (ExecutionException e) {
            logger.error(e);
            return null;
        }
    }

    public List<O> findAllFromCache() {
        return cache.asMap().values().stream().collect(Collectors.toList());
    }

    public void updateInCache(I id) {
        cache.refresh(id);
    }

    public void removeFromCache(I id) {
        cache.invalidate(id);
    }

    public void clearCache() {
        cache.invalidateAll();
        initCacheData(cache);
    }

}
