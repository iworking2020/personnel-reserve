package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.entity.Company;

import java.util.List;

public interface CompanyService {

    void deleteById(Long id);
    void deleteAll();

    List<Company> findAll();

    Company findById(Long id);

    void create(Company company);

    void update(Company company);

    void restartSequence();
    void restartSequence(Integer value);

}
