package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.entity.PeriodExperience;
import ru.iworking.personnel.reserve.repository.PeriodExperienceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeriodExperienceServiceImpl implements PeriodExperienceService {

    private final PeriodExperienceRepository periodExperienceRepository;

    @Override
    public List<PeriodExperience> findAll() {
        return periodExperienceRepository.findAll();
    }
}
