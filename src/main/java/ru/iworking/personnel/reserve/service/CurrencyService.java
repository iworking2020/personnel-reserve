package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.dao.CurrencyDao;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.entity.Currency;

public class CurrencyService extends DaoService<Currency, Long> {

    public static final CurrencyService INSTANCE = new CurrencyService();

    private CurrencyService() {}

    @Override
    public Dao<Currency, Long> getDao() {
        return CurrencyDao.INSTANCE;
    }
}
