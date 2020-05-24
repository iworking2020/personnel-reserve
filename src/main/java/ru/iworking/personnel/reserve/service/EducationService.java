package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.annotation.GuiceComponent;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.EducationDao;
import ru.iworking.personnel.reserve.entity.Education;

@GuiceComponent
public class EducationService extends DaoService<Education, Long> {
    @Override
    public Dao<Education, Long> getDao() {
        return EducationDao.INSTANCE;
    }
}
