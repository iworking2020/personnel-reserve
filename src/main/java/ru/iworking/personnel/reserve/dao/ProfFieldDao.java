package ru.iworking.personnel.reserve.dao;

import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.entity.ProfField;

import java.util.List;

@Component
public class ProfFieldDao extends Dao<ProfField, Long> {

    @Override
    public ProfField findById(Long id) {
        ProfField entity = (ProfField) getSessionProvider().getCurrentSession().get(ProfField.class, id);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ProfField> findAll() {
        List<ProfField> entities = (List<ProfField>) getSessionProvider().getCurrentSession().createQuery("from ProfField").list();
        return entities;
    }

}
