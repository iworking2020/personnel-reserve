package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.repository.CompanyRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final VacancyServiceImpl vacancyService;

    private final CompanyRepository companyRepository;

    @Transactional
    public void deleteById(Long aLong) {
        companyRepository.deleteById(aLong);
        vacancyService.deleteByCompanyId(aLong);
    }

    public void deleteAll() {
        companyRepository.deleteAll();
        vacancyService.deleteAll();
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void create(Company company) {
        companyRepository.save(company);
    }

    @Override
    public void update(Company company) {
        companyRepository.save(company);
    }
}
