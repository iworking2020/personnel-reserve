package ru.iworking.personnel.reserve.interfaces;

import java.util.List;

public interface Dao<T, K> {

    List<T> findAll();
    T find(K id);
    T create(T obj);
    T update(T obj);
    void delete(T obj);

}
