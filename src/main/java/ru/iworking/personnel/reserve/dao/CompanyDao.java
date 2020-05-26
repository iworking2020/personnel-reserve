package ru.iworking.personnel.reserve.dao;

import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.entity.Company;

import java.util.List;

@Component
public class CompanyDao extends Dao<Company, Long> {

    @Override
    public Company findById(Long id) {
        Company entity = (Company) getSessionProvider().getCurrentSession().get(Company.class, id);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Company> findAll() {
        List<Company> entities = (List<Company>) getSessionProvider().getCurrentSession().createQuery("from Company").list();
        return entities;
    }

}
