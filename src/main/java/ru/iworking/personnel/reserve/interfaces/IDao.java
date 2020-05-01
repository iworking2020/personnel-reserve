package ru.iworking.personnel.reserve.interfaces;

import java.io.Serializable;
import java.util.List;

public interface IDao<E, ID extends Serializable> {

    void persist(E entity);

    void update(E entity);

    E findById(ID id);

    void delete(E entity);

    List<E> findAll();

    void deleteAll();

}
