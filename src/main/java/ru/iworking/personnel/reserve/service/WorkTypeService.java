package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.annotation.GuiceComponent;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.WorkTypeDao;
import ru.iworking.personnel.reserve.entity.WorkType;

@GuiceComponent
public class WorkTypeService extends DaoService<WorkType, Long> {
    @Override
    public Dao<WorkType, Long> getDao() {
        return WorkTypeDao.INSTANCE;
    }
}
