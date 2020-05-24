package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.WorkTypeDao;
import ru.iworking.personnel.reserve.entity.WorkType;

public class WorkTypeService extends DaoService<WorkType, Long> {

    public static final WorkTypeService INSTANCE = new WorkTypeService();

    private WorkTypeService() {}

    @Override
    public Dao<WorkType, Long> getDao() {
        return WorkTypeDao.INSTANCE;
    }
}
