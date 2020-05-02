package ru.iworking.personnel.reserve.dao;

import ru.iworking.personnel.reserve.entity.CompanyType;

import java.util.List;

public class CompanyTypeDao extends Dao<CompanyType, Long> {

    public static final CompanyTypeDao INSTANCE = new CompanyTypeDao();

    private CompanyTypeDao() {}

    @Override
    public CompanyType findById(Long id) {
        CompanyType entity = (CompanyType) getSessionProvider().getCurrentSession().get(CompanyType.class, id);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CompanyType> findAll() {
        List<CompanyType> entities = (List<CompanyType>) getSessionProvider().getCurrentSession().createQuery("from CompanyType").list();
        return entities;
    }

}
