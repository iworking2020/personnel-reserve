package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.entity.ResumeState;

import java.util.List;

public interface ResumeStateService {
    List<ResumeState> findAll();

    ResumeState findById(Long id);
}
