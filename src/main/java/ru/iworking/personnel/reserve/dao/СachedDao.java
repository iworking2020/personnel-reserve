package ru.iworking.personnel.reserve.dao;

import ru.iworking.personnel.reserve.interfaces.Dao;

import java.io.Serializable;

public abstract class СachedDao<O, I extends Serializable> extends Сached<O, I> implements Dao<O, I> { }
