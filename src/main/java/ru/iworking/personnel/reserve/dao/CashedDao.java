package ru.iworking.personnel.reserve.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public abstract class CashedDao<O, I> extends Cashed<O, I> implements Dao<O, I> {

    private static final Logger logger = LogManager.getLogger(Cashed.class);

    public O findFromCash(I id) {
        try {
            return getLoadingCache().get(id);
        } catch (ExecutionException e) {
            logger.error(e);
            return null;
        }
    }

    public List<O> findAllFromCash() {
        return getLoadingCache().asMap().values().stream().collect(Collectors.toList());
    }

}
