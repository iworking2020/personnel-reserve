package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.dao.ProfFieldDao;
import ru.iworking.personnel.reserve.entity.ProfField;

@Service
public class ProfFieldService extends DaoService<ProfField, Long> {

    private final ProfFieldDao profFieldDao;

    @Autowired
    public ProfFieldService(ProfFieldDao profFieldDao) {
        this.profFieldDao = profFieldDao;
    }

    @Override
    public Dao<ProfField, Long> getDao() {
        return profFieldDao;
    }
}
