package ru.iworking.personnel.reserve.dao;

import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public abstract class Cashed<O, I> {

    private static final Logger logger = LogManager.getLogger(Cashed.class);

    private LoadingCache<I, O> cash;

    public Cashed() {
        initCash();
    }

    public void initCash() {
        cash = initLoadingCache();
        initCashData(cash);
    }

    public abstract LoadingCache<I, O> initLoadingCache();

    /**
     * Данный метод отвечает за заполнение хранилищя объектов
     * первоначальными данными.
     *
     * @param cash - созданное хранилище объектов кеширования
     */
    public void initCashData(LoadingCache<I, O> cash) {
        logger.debug("initCashData is empty");
    }

    public O findFromCash(I id) {
        try {
            return cash.get(id);
        } catch (ExecutionException e) {
            logger.error(e);
            return null;
        }
    }

    public List<O> findAllFromCash() {
        return cash.asMap().values().stream().collect(Collectors.toList());
    }

    public void updateInCash(I id) {
        cash.refresh(id);
    }

    public void removeFromCash(I id) {
        cash.invalidate(id);
    }

    public void clearCash() {
        cash.invalidateAll();
        initCashData(cash);
    }

}
