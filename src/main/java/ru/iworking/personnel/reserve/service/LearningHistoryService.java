package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.LearningHistoryDao;
import ru.iworking.personnel.reserve.entity.LearningHistory;

public class LearningHistoryService extends DaoService<LearningHistory, Long> {

    public static final LearningHistoryService INSTANCE = new LearningHistoryService();

    private LearningHistoryService() {}

    @Override
    public Dao<LearningHistory, Long> getDao() {
        return LearningHistoryDao.INSTANCE;
    }
}
