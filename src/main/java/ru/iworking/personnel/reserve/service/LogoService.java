package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.LogoDao;
import ru.iworking.personnel.reserve.entity.Logo;

@Service
public class LogoService extends DaoService<Logo, Long> {

    private final LogoDao logoDao;

    @Autowired
    public LogoService(LogoDao logoDao) {
        this.logoDao = logoDao;
    }

    @Override
    public LogoDao getDao() {
        return logoDao;
    }
}
