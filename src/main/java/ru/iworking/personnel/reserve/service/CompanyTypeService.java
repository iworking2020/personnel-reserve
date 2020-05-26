package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.CompanyTypeDao;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.entity.CompanyType;

@Service
public class CompanyTypeService extends DaoService<CompanyType, Long> {

    private final CompanyTypeDao companyTypeDao;

    @Autowired
    public CompanyTypeService(CompanyTypeDao companyTypeDao) {
        this.companyTypeDao = companyTypeDao;
    }

    @Override
    public Dao<CompanyType, Long> getDao() {
        return companyTypeDao;
    }
}
