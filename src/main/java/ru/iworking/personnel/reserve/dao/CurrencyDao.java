package ru.iworking.personnel.reserve.dao;

import org.springframework.stereotype.Component;
import ru.iworking.personnel.reserve.entity.Currency;

import java.util.List;

@Component
public class CurrencyDao extends Dao<Currency, Long> {

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
