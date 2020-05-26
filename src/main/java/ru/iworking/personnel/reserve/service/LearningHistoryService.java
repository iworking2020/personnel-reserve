package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.LearningHistoryDao;
import ru.iworking.personnel.reserve.entity.LearningHistory;

@Service
public class LearningHistoryService extends DaoService<LearningHistory, Long> {

    public final LearningHistoryDao learningHistoryDao;

    @Autowired
    public LearningHistoryService(LearningHistoryDao learningHistoryDao) {
        this.learningHistoryDao = learningHistoryDao;
    }

    @Override
    public Dao<LearningHistory, Long> getDao() {
        return learningHistoryDao;
    }
}
