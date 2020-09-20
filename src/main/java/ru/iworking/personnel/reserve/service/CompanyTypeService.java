package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.entity.CompanyType;

import java.util.List;

public interface CompanyTypeService {
    CompanyType findById(Long id);

    List<CompanyType> findAll();
}
