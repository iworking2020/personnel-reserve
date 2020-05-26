package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.VacancyDao;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.props.VacancyRequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VacancyService extends DaoService<Vacancy, Long> {

    private final VacancyDao vacancyDao;

    @Autowired
    public VacancyService(VacancyDao vacancyDao) {
        this.vacancyDao = vacancyDao;
    }

    @Override
    public VacancyDao getDao() {
        return vacancyDao;
    }

    public List<Vacancy> findAllByCompanyId(Long companyId) {
        Map<String, Object> params = new HashMap();
        params.put(VacancyRequestParam.COMPANY_ID, companyId);

        getDao().getSessionProvider().openCurrentSession();
        List<Vacancy> entities = getDao().findAll(params);
        getDao().getSessionProvider().closeCurrentSession();
        return entities;
    }

    public void deleteByCompanyId(Long companyId) {
        Map<String, Object> params = new HashMap();
        params.put(VacancyRequestParam.COMPANY_ID, companyId);

        getDao().getSessionProvider().openCurrentSessionwithTransaction();
        getDao().deleteAll(params);
        getDao().getSessionProvider().closeCurrentSessionwithTransaction();
    }


}
