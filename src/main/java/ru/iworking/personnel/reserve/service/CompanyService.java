package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.CompanyDao;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.entity.Company;

@Service
public class CompanyService extends DaoService<Company, Long> {

    private final VacancyService vacancyService;

    private final CompanyDao companyDao;

    @Autowired
    public CompanyService(VacancyService vacancyService, CompanyDao companyDao) {
        this.vacancyService = vacancyService;
        this.companyDao = companyDao;
    }

    @Override
    public Dao<Company, Long> getDao() {
        return companyDao;
    }

    @Override
    public void deleteById(Long aLong) {
        super.deleteById(aLong);
        vacancyService.deleteByCompanyId(aLong);
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
        vacancyService.deleteAll();
    }
}
