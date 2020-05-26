package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.ResumeStateDao;
import ru.iworking.personnel.reserve.entity.ResumeState;

@Service
public class ResumeStateService extends DaoService<ResumeState, Long> {

    private final ResumeStateDao resumeStateDao;

    @Autowired
    public ResumeStateService(ResumeStateDao resumeStateDao) {
        this.resumeStateDao = resumeStateDao;
    }

    @Override
    public Dao<ResumeState, Long> getDao() {
        return resumeStateDao;
    }
}
