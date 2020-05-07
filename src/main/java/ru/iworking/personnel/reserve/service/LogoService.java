package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.dao.LogoDao;
import ru.iworking.personnel.reserve.entity.Logo;

public class LogoService extends DaoService<Logo, Long> {

    public static final LogoService INSTANCE = new LogoService();

    private LogoService() {}

    @Override
    public LogoDao getDao() {
        return LogoDao.INSTANCE;
    }
}
