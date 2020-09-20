package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.entity.ProfField;
import ru.iworking.personnel.reserve.repository.ProfFieldRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfFieldServiceImpl implements ProfFieldService {

    private final ProfFieldRepository profFieldRepository;

    @Override
    public List<ProfField> findAll() {
        return profFieldRepository.findAll();
    }

    @Override
    public ProfField findById(Long id) {
        return profFieldRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
