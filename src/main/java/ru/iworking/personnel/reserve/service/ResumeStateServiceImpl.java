package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.entity.ResumeState;
import ru.iworking.personnel.reserve.repository.ResumeStateRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeStateServiceImpl implements ResumeStateService {

    private final ResumeStateRepository resumeStateRepository;

    @Override
    public List<ResumeState> findAll() {
        return resumeStateRepository.findAll();
    }

    @Override
    public ResumeState findById(Long id) {
        return resumeStateRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
