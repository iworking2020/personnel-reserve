package ru.iworking.personnel.reserve.dao;

import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.entity.WorkType;

import java.util.List;

@Component
public class WorkTypeDao extends Dao<WorkType, Long> {

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
