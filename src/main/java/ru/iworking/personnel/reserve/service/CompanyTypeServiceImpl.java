package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.entity.CompanyType;
import ru.iworking.personnel.reserve.repository.CompanyTypeRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyTypeServiceImpl implements CompanyTypeService {

    private final CompanyTypeRepository companyTypeRepository;

    @Override
    public CompanyType findById(Long id) {
        return companyTypeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<CompanyType> findAll() {
        return companyTypeRepository.findAll();
    }
}
