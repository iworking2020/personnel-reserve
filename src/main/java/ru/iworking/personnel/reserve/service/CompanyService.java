package ru.iworking.personnel.reserve.service;

import com.google.inject.Inject;
import ru.iworking.personnel.reserve.annotation.GuiceComponent;
import ru.iworking.personnel.reserve.dao.CompanyDao;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.entity.Company;

@GuiceComponent
public class CompanyService extends DaoService<Company, Long> {

    @Inject private VacancyService vacancyService;

    @Override
    public Dao<Company, Long> getDao() {
        return CompanyDao.INSTANCE;
    }

    @Override
    public void delete(Long aLong) {
        super.delete(aLong);
        vacancyService.deleteByCompanyId(aLong);
    }

    @Override
    public void deleteAll() {
        super.deleteAll();
        vacancyService.deleteAll();
    }
}
