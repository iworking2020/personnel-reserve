package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.ProfFieldDao;
import ru.iworking.personnel.reserve.entity.ProfField;

public class ProfFieldService extends DaoService<ProfField, Long> {

    public static final ProfFieldService INSTANCE = new ProfFieldService();

    private ProfFieldService() {}

    @Override
    public Dao<ProfField, Long> getDao() {
        return ProfFieldDao.INSTANCE;
    }
}
