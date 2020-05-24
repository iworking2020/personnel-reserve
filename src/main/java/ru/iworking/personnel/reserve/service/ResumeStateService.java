package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.annotation.GuiceComponent;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.ResumeStateDao;
import ru.iworking.personnel.reserve.entity.ResumeState;

@GuiceComponent
public class ResumeStateService extends DaoService<ResumeState, Long> {
    @Override
    public Dao<ResumeState, Long> getDao() {
        return ResumeStateDao.INSTANCE;
    }
}
