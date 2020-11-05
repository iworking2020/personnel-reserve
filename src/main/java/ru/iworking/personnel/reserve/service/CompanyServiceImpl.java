package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.repository.CompanyRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    @PersistenceContext
    private EntityManager entityManager;

    private final CompanyRepository companyRepository;

    @Transactional
    public void deleteById(Long aLong) {
        companyRepository.deleteById(aLong);
    }

    public void deleteAll() {
        companyRepository.deleteAll();
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

    @Override
    @Transactional
    public void restartSequence() {
        this.restartSequence(1000);
    }

    @Override
    @Transactional
    public void restartSequence(Integer value) {
        entityManager.createNativeQuery("ALTER SEQUENCE COMPANY_SEQ RESTART WITH :id")
                .setParameter("id" , value)
                .executeUpdate();
    }
}
