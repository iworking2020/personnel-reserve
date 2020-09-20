package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.entity.WorkType;
import ru.iworking.personnel.reserve.repository.WorkTypeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkTypeServiceImpl implements WorkTypeService {

    private final WorkTypeRepository workTypeRepository;

    @Override
    public List<WorkType> findAll() {
        return workTypeRepository.findAll();
    }

    @Override
    public WorkType findById(Long id) {
        return workTypeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
