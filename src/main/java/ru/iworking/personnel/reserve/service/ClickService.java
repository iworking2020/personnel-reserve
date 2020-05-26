package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.ClickDao;
import ru.iworking.personnel.reserve.entity.Click;
import ru.iworking.personnel.reserve.props.ClickRequestParam;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClickService extends DaoService<Click, Long> {

    private final ClickDao clickDao;

    @Autowired
    public ClickService(ClickDao clickDao) {
        this.clickDao = clickDao;
    }

    @Override
    public ClickDao getDao() {
        return clickDao;
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
