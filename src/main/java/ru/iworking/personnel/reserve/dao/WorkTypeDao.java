package ru.iworking.personnel.reserve.dao;

import ru.iworking.personnel.reserve.entity.WorkType;

import java.util.List;

public class WorkTypeDao extends Dao<WorkType, Long> {

    public static final WorkTypeDao INSTANCE = new WorkTypeDao();

    private WorkTypeDao() {}

    @Override
    public WorkType findById(Long id) {
        WorkType entity = (WorkType) getSessionProvider().getCurrentSession().get(WorkType.class, id);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<WorkType> findAll() {
        List<WorkType> entity = (List<WorkType>) getSessionProvider().getCurrentSession().createQuery("from WorkType").list();
        return entity;
    }

}
