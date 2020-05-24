package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.dao.CompanyDao;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.entity.Company;

public class CompanyService extends DaoService<Company, Long> {

    public static final CompanyService INSTANCE = new CompanyService();

    private final VacancyService vacancyService = VacancyService.INSTANCE;

    private CompanyService() {}

    @Override
    public Dao<Company, Long> getDao() {
        return CompanyDao.INSTANCE;
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
