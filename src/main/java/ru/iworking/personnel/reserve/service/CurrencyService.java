package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.entity.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> findAll();

    Currency findById(Long id);
}
