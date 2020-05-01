package ru.iworking.personnel.reserve.dao;

import ru.iworking.personnel.reserve.interfaces.IDao;
import ru.iworking.personnel.reserve.provider.SessionProvider;

import java.io.Serializable;
import java.util.List;

public abstract class Dao<E, ID extends Serializable> implements IDao<E, ID> {

    private SessionProvider sessionProvider = new SessionProvider();

    public SessionProvider getSessionProvider() {
        return sessionProvider;
    }
    public void setSessionProvider(SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public void persist(E entity) {
        sessionProvider.getCurrentSession().save(entity);
    }

    public void update(E entity) {
        sessionProvider.getCurrentSession().update(entity);
    }

    public abstract E findById(ID id);

    public void delete(E entity) {
        sessionProvider.getCurrentSession().delete(entity);
    }

    public abstract List<E> findAll();

    public void deleteAll() {
        List<E> entityList = findAll();
        for (E entity : entityList) {
            delete(entity);
        }
    }

}
