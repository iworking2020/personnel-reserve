package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.ResumeStateDao;
import ru.iworking.personnel.reserve.entity.ResumeState;

public class ResumeStateService extends DaoService<ResumeState, Long> {

    public static final ResumeStateService INSTANCE = new ResumeStateService();

    private ResumeStateService() {}

    @Override
    public Dao<ResumeState, Long> getDao() {
        return ResumeStateDao.INSTANCE;
    }
}
