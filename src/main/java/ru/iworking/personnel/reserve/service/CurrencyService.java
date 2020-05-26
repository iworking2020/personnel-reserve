package ru.iworking.personnel.reserve.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.dao.CurrencyDao;
import ru.iworking.personnel.reserve.dao.Dao;
import ru.iworking.personnel.reserve.entity.Currency;

@Service
public class CurrencyService extends DaoService<Currency, Long> {

    private final CurrencyDao currencyDao;

    @Autowired
    public CurrencyService(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Override
    public Dao<Currency, Long> getDao() {
        return currencyDao;
    }
}
