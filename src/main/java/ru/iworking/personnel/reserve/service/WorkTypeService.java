package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.WorkTypeDao;
import ru.iworking.personnel.reserve.entity.WorkType;

@Service
public class WorkTypeService extends DaoService<WorkType, Long> {

    private final WorkTypeDao workTypeDao;

    @Autowired
    public WorkTypeService(WorkTypeDao workTypeDao) {
        this.workTypeDao = workTypeDao;
    }

    @Override
    public Dao<WorkType, Long> getDao() {
        return workTypeDao;
    }
}
