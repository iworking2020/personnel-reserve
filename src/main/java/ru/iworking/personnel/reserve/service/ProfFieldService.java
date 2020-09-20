package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.entity.ProfField;

import java.util.List;

public interface ProfFieldService {
    List<ProfField> findAll();

    ProfField findById(Long id);
}
