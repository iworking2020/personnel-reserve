package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.annotation.GuiceComponent;
import ru.iworking.personnel.reserve.dao.CurrencyDao;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.entity.Currency;

@GuiceComponent
public class CurrencyService extends DaoService<Currency, Long> {

    @Override
    public Dao<Currency, Long> getDao() {
        return CurrencyDao.INSTANCE;
    }
}
