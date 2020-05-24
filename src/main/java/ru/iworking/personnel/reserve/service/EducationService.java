package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.EducationDao;
import ru.iworking.personnel.reserve.entity.Education;

public class EducationService extends DaoService<Education, Long> {

    public static final EducationService INSTANCE = new EducationService();

    private EducationService() {}

    @Override
    public Dao<Education, Long> getDao() {
        return EducationDao.INSTANCE;
    }
}
