package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.annotation.GuiceComponent;
import ru.iworking.personnel.reserve.dao.LogoDao;
import ru.iworking.personnel.reserve.entity.Logo;

@GuiceComponent
public class LogoService extends DaoService<Logo, Long> {
    @Override
    public LogoDao getDao() {
        return LogoDao.INSTANCE;
    }
}
