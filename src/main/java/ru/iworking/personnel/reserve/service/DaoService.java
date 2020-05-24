package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.dao.Dao;

import java.io.Serializable;
import java.util.List;

public abstract class DaoService<E, ID extends Serializable> {
    
    public abstract Dao<E, ID> getDao();

    public void persist(E entity) {
        getDao().getSessionProvider().openCurrentSessionwithTransaction();
        getDao().persist(entity);
        getDao().getSessionProvider().closeCurrentSessionwithTransaction();
    }

    public void update(E entity) {
        getDao().getSessionProvider().openCurrentSessionwithTransaction();
        getDao().update(entity);
        getDao().getSessionProvider().closeCurrentSessionwithTransaction();
    }

    public E findById(ID id) {
        getDao().getSessionProvider().openCurrentSession();
        E entity = getDao().findById(id);
        getDao().getSessionProvider().closeCurrentSession();
        return entity;
    }

    public void deleteById(ID id) {
        getDao().getSessionProvider().openCurrentSessionwithTransaction();
        E entity = getDao().findById(id);
        getDao().delete(entity);
        getDao().getSessionProvider().closeCurrentSessionwithTransaction();
    }

    public List<E> findAll() {
        getDao().getSessionProvider().openCurrentSession();
        List<E> entities = getDao().findAll();
        getDao().getSessionProvider().closeCurrentSession();
        return entities;
    }

    public void deleteAll() {
        getDao().getSessionProvider().openCurrentSessionwithTransaction();
        getDao().deleteAll();
        getDao().getSessionProvider().closeCurrentSessionwithTransaction();
    }
    
}
