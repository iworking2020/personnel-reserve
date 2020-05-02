package ru.iworking.personnel.reserve.dao;

import ru.iworking.personnel.reserve.entity.LearningHistory;

import java.util.List;

public class LearningHistoryDao extends Dao<LearningHistory, Long> {

    public static final LearningHistoryDao INSTANCE = new LearningHistoryDao();

    private LearningHistoryDao() {}

    @Override
    public LearningHistory findById(Long id) {
        LearningHistory entity = (LearningHistory) getSessionProvider().getCurrentSession().get(LearningHistory.class, id);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LearningHistory> findAll() {
        List<LearningHistory> books = (List<LearningHistory>) getSessionProvider().getCurrentSession().createQuery("from LearningHistory").list();
        return books;
    }

}
