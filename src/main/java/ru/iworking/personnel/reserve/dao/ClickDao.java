package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import ru.iworking.personnel.reserve.entity.Click;
import ru.iworking.personnel.reserve.props.ClickRequestParam;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class ClickDao extends Dao<Click, Long> {

    public static final ClickDao INSTANCE = new ClickDao();

    private ClickDao() {}

    @Override
    public Click findById(Long id) {
        Click entity = (Click) getSessionProvider().getCurrentSession().get(Click.class, id);
        return entity;
    }

    public Long count(Map<String, Object> params) {
        Long count = (Long) createQuery("select COUNT(click) from Click as click where 1=1", params, Long.class).getSingleResult();
        return count;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Click> findAll() {
        List<Click> entities = (List<Click>) getSessionProvider().getCurrentSession().createQuery("from Click").list();
        return entities;
    }

    @SuppressWarnings("unchecked")
    public List<Click> findAll(Map<String, Object> params) {
        List<Click> list = createQuery("from Click as click where 1=1", params, Click.class).getResultList();
        return list;
    }

    private Query createQuery(String sql, Map<String, Object> params, Class<?> clazz) {
        Long vacancyId = params.get(ClickRequestParam.VACANCY_ID) != null ? Long.valueOf(params.get(ClickRequestParam.VACANCY_ID).toString()) : null;
        Long resumeId = params.get(ClickRequestParam.RESUME_ID) != null ? Long.valueOf(params.get(ClickRequestParam.RESUME_ID).toString()) : null;

        if (vacancyId != null) sql += " and click.vacancy.id = :vacancyId";
        if (resumeId != null) sql += " and click.resume.id = :resumeId";

        Session session = getSessionProvider().getCurrentSession();

        Query query = clazz != null ? session.createQuery(sql, clazz) : session.createQuery(sql);

        if (vacancyId != null) query.setParameter("vacancyId", vacancyId);
        if (resumeId != null) query.setParameter("resumeId", resumeId);

        return query;
    }

}
