package ru.iworking.personnel.reserve.dao;

import ru.iworking.personnel.reserve.entity.Currency;

import java.util.List;

public class CurrencyDao extends Dao<Currency, Long> {

    public static final CurrencyDao INSTANCE = new CurrencyDao();

    private CurrencyDao() {}

    @Override
    public Currency findById(Long id) {
        Currency entity = (Currency) getSessionProvider().getCurrentSession().get(Currency.class, id);
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Currency> findAll() {
        List<Currency> entities = (List<Currency>) getSessionProvider().getCurrentSession().createQuery("from Currency").list();
        return entities;
    }

}
