package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.dao.CompanyTypeDao;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.entity.CompanyType;

public class CompanyTypeService extends DaoService<CompanyType, Long> {

    public static final CompanyTypeService INSTANCE = new CompanyTypeService();

    @Override
    public Dao<CompanyType, Long> getDao() {
        return CompanyTypeDao.INSTANCE;
    }
}
