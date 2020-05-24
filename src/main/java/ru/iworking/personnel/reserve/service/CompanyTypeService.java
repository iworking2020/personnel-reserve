package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.annotation.GuiceComponent;
import ru.iworking.personnel.reserve.dao.CompanyTypeDao;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.entity.CompanyType;

@GuiceComponent
public class CompanyTypeService extends DaoService<CompanyType, Long> {

    @Override
    public Dao<CompanyType, Long> getDao() {
        return CompanyTypeDao.INSTANCE;
    }

}
