package ru.iworking.personnel.reserve.service;

import ru.iworking.personnel.reserve.entity.Click;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ClickService {

    Click findById(Long id);

    Long count(Map<String, Object> params);
    Long countByVacancyIdAndResumeId(Long vacancyId, Long resumeId);

    List<Click> findAll();
    List<Click> findAll(Map<String, Object> params);

    void create(Click click);

    Click update(Click click);

    void delete(Click click);
    void deleteById(Long id);

    void deleteAll(Collection<Click> clicks);
}
