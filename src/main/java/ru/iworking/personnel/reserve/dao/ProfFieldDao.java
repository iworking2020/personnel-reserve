package ru.iworking.personnel.reserve.dao;

import ru.iworking.personnel.reserve.entity.ProfField;

import java.util.List;

public class ProfFieldDao extends Dao<ProfField, Long> {

    public static final ProfFieldDao INSTANCE = new ProfFieldDao();

    private ProfFieldDao() {}

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
