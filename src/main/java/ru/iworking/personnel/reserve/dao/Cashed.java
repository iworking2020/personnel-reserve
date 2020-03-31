package ru.iworking.personnel.reserve.dao;

import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Cashed<O, I> {

    private static final Logger logger = LogManager.getLogger(Cashed.class);

    private LoadingCache<I, O> cash;

    protected LoadingCache<I, O> getLoadingCache() {
        return cash;
    }

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

    public void clearCash() {
        cash.invalidateAll();
        initCashData(cash);
    }

}
