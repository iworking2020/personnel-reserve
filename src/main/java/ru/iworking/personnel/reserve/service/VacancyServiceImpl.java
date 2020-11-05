package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.Vacancy;
import ru.iworking.personnel.reserve.props.VacancyRequestParam;
import ru.iworking.personnel.reserve.repository.VacancyRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    @PersistenceContext
    private EntityManager entityManager;

    private final VacancyRepository vacancyRepository;
    private final ClickService clickService;

    @Override
    public Vacancy findById(Long id) {
        return vacancyRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Long count(Map<String, Object> params) {
        Long count = (Long) createQuery("select COUNT(vacancy) from Vacancy as vacancy where 1=1", params).getSingleResult();
        return count;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Vacancy> findAll(Map<String, Object> params) {
        List<Vacancy> list = createQuery("from Vacancy as vacancy where 1=1", params).getResultList();
        return list;
    }

    @Override
    public List<Vacancy> findAllByCompany(Company company) {
        return vacancyRepository.findAllByCompany(company);
    }

    @Override
    public void deleteAll() {
        vacancyRepository.deleteAll();
    }

    @Override
    @Transactional
    public void deleteAll(Collection<Vacancy> vacancies) {
        vacancies.forEach(vacancy -> this.deleteById(vacancy.getId()));
    }

    @Override
    public void create(Vacancy vacancy) {
        vacancyRepository.save(vacancy);
    }

    @Override
    public void update(Vacancy vacancy) {
        vacancyRepository.save(vacancy);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        final Vacancy vacancy = this.findById(id);
        clickService.deleteAll(vacancy.getClicks());
        vacancyRepository.delete(vacancy);
    }

    @Override
    @Transactional
    public void restartSequence() {
        this.restartSequence(1000);
    }

    @Override
    @Transactional
    public void restartSequence(Integer value) {
        entityManager.createNativeQuery("ALTER SEQUENCE VACANCY_SEQ RESTART WITH :id")
                .setParameter("id", value)
                .executeUpdate();
    }

    private Query createQuery(String sql, Map<String, Object> params) {
        Long companyId = params.get(VacancyRequestParam.COMPANY_ID) != null ? Long.valueOf(params.get(VacancyRequestParam.COMPANY_ID).toString()) : null;
        if (companyId != null) sql += " and vacancy.companyId = :companyId";
        Query query = entityManager.createQuery(sql);
        if (companyId != null) query.setParameter("companyId", companyId);
        return query;
    }

}
