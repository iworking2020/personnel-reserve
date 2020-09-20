package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.entity.Resume;

import java.util.List;
import java.util.Map;

public interface ResumeService {

    Long count(Map<String, Object> params);
    Long countByResumeStateId(Long resumeStateId);

    Resume findById(Long id);

    List<Resume> findAll();
    List<Resume> findAll(Map<String, Object> params);
    List<Resume> findAllByProfFieldId(Long profFieldId);
    List<Resume> findAllByResumeStateId(Long resumeStateId);

    void create(Resume resume);

    void update(Resume resume);

    void deleteById(Long id);
}
