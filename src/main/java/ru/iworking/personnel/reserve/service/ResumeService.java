package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.dao.ResumeDao;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.props.ResumeRequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeService extends DaoService<Resume, Long> {

    public static final ResumeService INSTANCE = new ResumeService();

    private ResumeService() {}

    @Override
    public ResumeDao getDao() {
        return ResumeDao.INSTANCE;
    }

    public List<Resume> findAll(Map<String, Object> params) {
        getDao().getSessionProvider().openCurrentSession();
        List<Resume> entities = getDao().findAll(params);
        getDao().getSessionProvider().closeCurrentSession();
        return entities;
    }

    public Long countByResumeStateId(Long resumeStateId) {
        Map<String, Object> params = new HashMap();
        params.put(ResumeRequestParam.RESUME_STATE_ID, resumeStateId);

        getDao().getSessionProvider().openCurrentSession();
        Long count = getDao().count(params);
        getDao().getSessionProvider().closeCurrentSession();
        return count;
    }

    public List<Resume> findAllByProfFieldId(Long profFieldId) {
        Map<String, Object> params = new HashMap();
        params.put(ResumeRequestParam.PROF_FIELD_ID, profFieldId);

        getDao().getSessionProvider().openCurrentSession();
        List<Resume> entities = getDao().findAll(params);
        getDao().getSessionProvider().closeCurrentSession();
        return entities;
    }

    public List<Resume> findAllByResumeStateId(Long resumeStateId) {
        Map<String, Object> params = new HashMap();
        params.put(ResumeRequestParam.RESUME_STATE_ID, resumeStateId);

        getDao().getSessionProvider().openCurrentSession();
        List<Resume> entities = getDao().findAll(params);
        getDao().getSessionProvider().closeCurrentSession();
        return entities;
    }
}
