package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.props.ResumeRequestParam;
import ru.iworking.personnel.reserve.repository.ResumeRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ResumeRepository resumeRepository;
    private final ClickService clickService;

    @Override
    public Long count(Map<String, Object> params) {
        Long count = (Long) createQuery("select COUNT(resume) from Resume as resume where 1=1", params, Long.class).getSingleResult();
        return count;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Resume> findAll(Map<String, Object> params) {
        List<Resume> list = createQuery("from Resume as resume where 1=1", params, Resume.class).getResultList();
        return list;
    }

    @Override
    public Long countByResumeStateId(Long resumeStateId) {
        Map<String, Object> params = new HashMap();
        params.put(ResumeRequestParam.RESUME_STATE_ID, resumeStateId);
        Long count = this.count(params);
        return count;
    }

    @Override
    public Resume findById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Resume> findAll() {
        return resumeRepository.findAll();
    }

    @Override
    public List<Resume> findAllByProfFieldId(Long profFieldId) {
        Map<String, Object> params = new HashMap();
        params.put(ResumeRequestParam.PROF_FIELD_ID, profFieldId);

        List<Resume> entities = this.findAll(params);
        return entities;
    }

    @Override
    public List<Resume> findAllByResumeStateId(Long resumeStateId) {
        Map<String, Object> params = new HashMap();
        params.put(ResumeRequestParam.RESUME_STATE_ID, resumeStateId);

        List<Resume> entities = this.findAll(params);
        return entities;
    }

    @Override
    public void create(Resume resume) {
        resumeRepository.save(resume);
    }

    @Override
    public void update(Resume resume) {
        resumeRepository.save(resume);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        final Resume resume = this.findById(id);
        clickService.deleteAll(resume.getClicks());
        resumeRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        resumeRepository.deleteAll();
    }

    private Query createQuery(String sql, Map<String, Object> params, Class<?> clazz) {
        String firstName = params.get(ResumeRequestParam.FIRST_NAME) != null ? params.get(ResumeRequestParam.FIRST_NAME).toString() : null;
        String lastName = params.get(ResumeRequestParam.LAST_NAME) != null ? params.get(ResumeRequestParam.LAST_NAME).toString() : null;
        String middleName = params.get(ResumeRequestParam.MIDDLE_NAME) != null ? params.get(ResumeRequestParam.MIDDLE_NAME).toString() : null;
        String profession = params.get(ResumeRequestParam.PROFESSION) != null ? params.get(ResumeRequestParam.PROFESSION).toString() : null;
        BigDecimal wage = params.get(ResumeRequestParam.WAGE) != null ? (BigDecimal) params.get(ResumeRequestParam.WAGE) : null;
        Long profFieldId = params.get(ResumeRequestParam.PROF_FIELD_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.PROF_FIELD_ID).toString()) : null;
        Long educationId = params.get(ResumeRequestParam.EDUCATION_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.EDUCATION_ID).toString()) : null;
        Long workTypeId = params.get(ResumeRequestParam.WORK_TYPE_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.WORK_TYPE_ID).toString()) : null;
        Long resumeStateId = params.get(ResumeRequestParam.RESUME_STATE_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.RESUME_STATE_ID).toString()) : null;

        if (lastName != null) sql += " and resume.profile.lastName like :lastName";
        if (firstName != null) sql += " and resume.profile.firstName like :firstName";
        if (middleName != null) sql += " and resume.profile.middleName like :middleName";
        if (profession != null) sql += " and resume.profession like :profession";
        if (profFieldId != null) sql += " and resume.profFieldId = :profFieldId";
        if (educationId != null) sql += " and resume.educationId = :educationId";
        if (workTypeId != null) sql += " and resume.workTypeId = :workTypeId";
        if (wage != null) sql += " and resume.wage.count = :wage";
        if (resumeStateId != null) sql += " and resume.state.id = :resumeStateId";

        Query query = entityManager.createQuery(sql);

        if (lastName != null) query.setParameter("lastName", lastName);
        if (firstName != null) query.setParameter("firstName", firstName);
        if (profession != null) query.setParameter("profession", profession);
        if (middleName != null) query.setParameter("middleName", middleName);
        if (profFieldId != null) query.setParameter("profFieldId", profFieldId);
        if (educationId != null) query.setParameter("educationId", educationId);
        if (workTypeId != null) query.setParameter("workTypeId", workTypeId);
        if (wage != null) query.setParameter("wage", wage);
        if (resumeStateId != null) query.setParameter("resumeStateId", resumeStateId);

        return query;
    }

    @Override
    @Transactional
    public void restartSequence() {
        this.restartSequence(1000);
    }

    @Override
    @Transactional
    public void restartSequence(Integer value) {
        entityManager.createNativeQuery("ALTER SEQUENCE RESUME_SEQ RESTART WITH :id")
                .setParameter("id", value)
                .executeUpdate();

    }
}
