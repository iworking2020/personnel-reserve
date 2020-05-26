package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.EducationDao;
import ru.iworking.personnel.reserve.entity.Education;

@Service
public class EducationService extends DaoService<Education, Long> {

    private final EducationDao educationDao;

    @Autowired
    public EducationService(EducationDao educationDao) {
        this.educationDao = educationDao;
    }

    @Override
    public Dao<Education, Long> getDao() {
        return educationDao;
    }
}
