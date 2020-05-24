package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.annotation.GuiceComponent;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.ProfFieldDao;
import ru.iworking.personnel.reserve.entity.ProfField;

@GuiceComponent
public class ProfFieldService extends DaoService<ProfField, Long> {
    @Override
    public Dao<ProfField, Long> getDao() {
        return ProfFieldDao.INSTANCE;
    }
}
