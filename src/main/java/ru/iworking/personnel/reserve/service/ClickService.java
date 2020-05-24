package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.annotation.GuiceComponent;
import ru.iworking.personnel.reserve.dao.ClickDao;
import ru.iworking.personnel.reserve.entity.Click;
import ru.iworking.personnel.reserve.props.ClickRequestParam;

import java.util.HashMap;
import java.util.Map;

@GuiceComponent
public class ClickService extends DaoService<Click, Long> {

    @Override
    public ClickDao getDao() {
        return ClickDao.INSTANCE;
    }

    public Long countByVacancyIdAndResumeId(Long vacancyId, Long resumeId) {
        Map<String, Object> params = new HashMap();
        params.put(ClickRequestParam.VACANCY_ID, vacancyId);
        params.put(ClickRequestParam.RESUME_ID, resumeId);

        getDao().getSessionProvider().openCurrentSession();
        Long count = getDao().count(params);
        getDao().getSessionProvider().closeCurrentSession();
        return count;
    }
}
