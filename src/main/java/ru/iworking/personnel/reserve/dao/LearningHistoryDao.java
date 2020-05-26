package ru.iworking.personnel.reserve.dao;

import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.entity.LearningHistory;

import java.util.List;

@Component
public class LearningHistoryDao extends Dao<LearningHistory, Long> {

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
