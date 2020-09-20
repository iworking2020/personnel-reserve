package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.entity.Education;

import java.util.List;

public interface EducationService {
    List<Education> findAll();

    Education findById(Long id);
}
