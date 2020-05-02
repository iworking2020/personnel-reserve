package ru.iworking.personnel.reserve.dao;

import ru.iworking.personnel.reserve.entity.ResumeState;

import java.util.List;

public class ResumeStateDao extends Dao<ResumeState, Long> {

    public static final ResumeStateDao INSTANCE = new ResumeStateDao();

    private ResumeStateDao() {}

    @Override
    public ResumeState findById(Long id) {
        ResumeState entity = (ResumeState) getSessionProvider().getCurrentSession().get(ResumeState.class, id);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ResumeState> findAll() {
        List<ResumeState> entities = (List<ResumeState>) getSessionProvider().getCurrentSession().createQuery("from ResumeState").list();
        return entities;
    }

}
