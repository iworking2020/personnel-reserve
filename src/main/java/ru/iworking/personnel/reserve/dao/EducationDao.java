package ru.iworking.personnel.reserve.dao;

import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.entity.Education;

import java.util.List;

@Component
public class EducationDao extends Dao<Education, Long> {

    @Override
    public Education findById(Long id) {
        Education education = (Education) getSessionProvider().getCurrentSession().get(Education.class, id);
        return education;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Education> findAll() {
        List<Education> books = (List<Education>) getSessionProvider().getCurrentSession().createQuery("from Education").list();
        return books;
    }

}
