package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.entity.Vacancy;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface VacancyService {

    Vacancy findById(Long id);

    Long count(Map<String, Object> params);

    List<Vacancy> findAll();
    List<Vacancy> findAllByCompanyId(Long companyId);
    List<Vacancy> findAll(Map<String, Object> params);

    void deleteAll();
    void deleteByCompanyId(Long companyId);
    void deleteAll(Collection<Vacancy> vacancies);

    void create(Vacancy vacancy);

    void update(Vacancy vacancy);

    void deleteById(Long id);
}
