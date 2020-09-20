package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iworking.personnel.reserve.entity.Click;
import ru.iworking.personnel.reserve.props.ClickRequestParam;
import ru.iworking.personnel.reserve.repository.ClickRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClickServiceImpl implements ClickService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ClickRepository clickRepository;

    @Override
    public Click findById(Long id) {
        return clickRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Click update(Click updateClick) {
        return clickRepository.save(updateClick);
    }

    @Override
    public void delete(Click click) {
        this.deleteById(click.getId());
    }

    @Override
    public Long count(Map<String, Object> params) {
        Long count = (Long) createQuery("select COUNT(click) from Click as click where 1=1", params).getSingleResult();
        return count;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Click> findAll() {
        return clickRepository.findAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Click> findAll(Map<String, Object> params) {
        List<Click> list = createQuery("from Click as click where 1=1", params).getResultList();
        return list;
    }

    @Override
    public void create(Click click) {
        clickRepository.save(click);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Query query = createQuery("delete from Click as click where click.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteAll(Collection<Click> clicks) {
        clicks.forEach(click -> this.deleteById(click.getId()));
    }

    @Override
    public Long countByVacancyIdAndResumeId(Long vacancyId, Long resumeId) {
        Map<String, Object> params = new HashMap();
        params.put(ClickRequestParam.VACANCY_ID, vacancyId);
        params.put(ClickRequestParam.RESUME_ID, resumeId);

        Long count = this.count(params);
        return count;
    }

    private Query createQuery(String sql) {
        return this.createQuery(sql, new HashMap<>());
    }

    private Query createQuery(String sql, Map<String, Object> params) {
        Long vacancyId = params.get(ClickRequestParam.VACANCY_ID) != null ? Long.valueOf(params.get(ClickRequestParam.VACANCY_ID).toString()) : null;
        Long resumeId = params.get(ClickRequestParam.RESUME_ID) != null ? Long.valueOf(params.get(ClickRequestParam.RESUME_ID).toString()) : null;

        if (vacancyId != null) sql += " and click.vacancy.id = :vacancyId";
        if (resumeId != null) sql += " and click.resume.id = :resumeId";

        Query query = entityManager.createQuery(sql);

        if (vacancyId != null) query.setParameter("vacancyId", vacancyId);
        if (resumeId != null) query.setParameter("resumeId", resumeId);

        return query;
    }
}
