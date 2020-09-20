package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.entity.WorkType;

import java.util.List;

public interface WorkTypeService {
    List<WorkType> findAll();

    WorkType findById(Long id);
}
