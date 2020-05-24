package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.annotation.GuiceComponent;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.LearningHistoryDao;
import ru.iworking.personnel.reserve.entity.LearningHistory;

@GuiceComponent
public class LearningHistoryService extends DaoService<LearningHistory, Long> {
    @Override
    public Dao<LearningHistory, Long> getDao() {
        return LearningHistoryDao.INSTANCE;
    }
}
