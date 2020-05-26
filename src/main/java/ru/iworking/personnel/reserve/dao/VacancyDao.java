package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.props.VacancyRequestParam;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Component
public class VacancyDao extends Dao<Vacancy, Long> {

    @Override
    public Vacancy findById(Long id) {
        Vacancy entity = (Vacancy) getSessionProvider().getCurrentSession().get(Vacancy.class, id);
        return entity;
    }

    public Long count(Map<String, Object> params) {
        Long count = (Long) createQuery("select COUNT(vacancy) from Vacancy as vacancy where 1=1", params, Long.class).getSingleResult();
        return count;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Vacancy> findAll() {
        List<Vacancy> entities = (List<Vacancy>) getSessionProvider().getCurrentSession().createQuery("from Vacancy").list();
        return entities;
    }

    @SuppressWarnings("unchecked")
    public List<Vacancy> findAll(Map<String, Object> params) {
        List<Vacancy> list = createQuery("from Vacancy as vacancy where 1=1", params, Vacancy.class).getResultList();
        return list;
    }

    public void deleteAll(Map<String, Object> params) {
        createQuery("delete Vacancy as vacancy where 1=1", params, null).executeUpdate();
    }

    private Query createQuery(String sql, Map<String, Object> params, Class<?> clazz) {
        Long companyId = params.get(VacancyRequestParam.COMPANY_ID) != null ? Long.valueOf(params.get(VacancyRequestParam.COMPANY_ID).toString()) : null;

        if (companyId != null) sql += " and vacancy.companyId = :companyId";

        Session session = getSessionProvider().getCurrentSession();

        Query query = clazz != null ? session.createQuery(sql, clazz) : session.createQuery(sql);

        if (companyId != null) query.setParameter("companyId", companyId);

        return query;
    }

}
