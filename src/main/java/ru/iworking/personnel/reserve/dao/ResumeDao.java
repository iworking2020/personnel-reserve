package ru.iworking.personnel.reserve.dao;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.iworking.personnel.reserve.entity.ProfField;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.props.ResumeRequestParam;
import ru.iworking.personnel.reserve.utils.HibernateUtil;
import ru.iworking.service.api.PersistenceSeparator;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ResumeDao extends CashedDao<Resume, Long> {

    private static volatile ResumeDao instance;

    @Override
    public LoadingCache<Long, Resume> initLoadingCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build(new CacheLoader<Long, Resume>() {
                    @Override
                    public Resume load(Long key) throws Exception {
                        return ResumeDao.getInstance().find(key);
                    }
                });
    }

    @Override
    public void initCashData(LoadingCache<Long, Resume> cash) {
        //cash.putAll(findAll().stream().collect(Collectors.toMap(Resume::getId, Function.identity())));
    }

    public List<Resume> findAllByProfFieldFromCash(ProfField profField) {
        return findAllFromCash().stream().filter(r -> r.getProfFieldId() == profField.getId()).collect(Collectors.toList());
    }

    public List<Resume> findAllByResumeStateIdFromCash(Long resumeStateId) {
        return findAllFromCash().stream().filter(r -> r.getState() != null && r.getState().getId() == resumeStateId).collect(Collectors.toList());
    }

    public Resume createAndUpdateInCash(Resume resume) {
        Resume resume1 = create(resume);
        updateInCash(resume1.getId());
        return resume1;
    }

    public Resume updateAndUpdateInCash(Resume resume) {
        Resume resume1 = update(resume);
        updateInCash(resume1.getId());
        return resume1;
    }

    public void deleteAndRemoveFromCash(Resume resume) {
        delete(resume);
        removeFromCash(resume.getId());
    }

    public List<Resume> findAllFromCash(Map<String, Object> params) {
        String firstName = params.get(ResumeRequestParam.FIRST_NAME) != null ? params.get(ResumeRequestParam.FIRST_NAME).toString() : null;
        String lastName = params.get(ResumeRequestParam.LAST_NAME) != null ? params.get(ResumeRequestParam.LAST_NAME).toString() : null;
        String middleName = params.get(ResumeRequestParam.MIDDLE_NAME) != null ? params.get(ResumeRequestParam.MIDDLE_NAME).toString() : null;
        String profession = params.get(ResumeRequestParam.PROFESSION) != null ? params.get(ResumeRequestParam.PROFESSION).toString() : null;
        BigDecimal wage = params.get(ResumeRequestParam.WAGE) != null ? (BigDecimal) params.get(ResumeRequestParam.WAGE) : null;
        Long profFieldId = params.get(ResumeRequestParam.PROF_FIELD_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.PROF_FIELD_ID).toString()) : null;
        Long educationId = params.get(ResumeRequestParam.EDUCATION_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.EDUCATION_ID).toString()) : null;
        Long workTypeId = params.get(ResumeRequestParam.WORK_TYPE_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.WORK_TYPE_ID).toString()) : null;
        Long resumeStateId = params.get(ResumeRequestParam.RESUME_STATE_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.RESUME_STATE_ID).toString()) : null;

        return findAllFromCash().stream()
                .filter(resume -> firstName == null ? true : resume.getProfile() != null && resume.getProfile().getFirstName().equals(firstName))
                .filter(resume -> lastName == null ? true : resume.getProfile() != null && resume.getProfile().getLastName().equals(lastName))
                .filter(resume -> middleName == null ? true : resume.getProfile() != null && resume.getProfile().getMiddleName().equals(middleName))
                .filter(resume -> profession == null ? true : resume.getProfession().equals(profession))
                .filter(resume -> wage == null ? true : resume.getWage() != null && resume.getWage().getCountBigDecimal().equals(wage))
                .filter(resume -> profFieldId == null ? true : resume.getProfFieldId() == profFieldId)
                .filter(resume -> educationId == null ? true : resume.getEducationId() == educationId)
                .filter(resume -> workTypeId == null ? true : resume.getWorkTypeId() == workTypeId)
                .filter(resume -> resumeStateId == null ? true : resume.getState() != null && resume.getState().getId() == resumeStateId)
                .collect(Collectors.toList());
    }

    public List<Resume> findAllByProfField(ProfField profField) {
        Map<String, Object> params = new HashMap();
        params.put(ResumeRequestParam.PROF_FIELD_ID, profField.getId());
        return findAll(params);
    }

    public List<Resume> findAllByResumeStateId(Long resumeStateId) {
        Map<String, Object> params = new HashMap();
        params.put(ResumeRequestParam.RESUME_STATE_ID, resumeStateId);
        return findAll(params);
    }

    public Long countByResumeStateId(Long id) {
        Long count = 0L;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("select COUNT(resume) from Resume as resume where resume.state.id = :id", Long.class);
            query.setParameter("id", id);
            count = (Long) query.getSingleResult();
            session.flush();
            transaction.commit();
        }
        return count;
    }

    @Override
    public List<Resume> findAll() {
        List<Resume> list;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            list = session.createQuery("FROM Resume", Resume.class).list();
            session.flush();
            transaction.commit();
        }
        return list;
    }
    
    public List<Resume> findAll(Map<String, Object> params) {
        PersistenceSeparator separator = new PersistenceSeparator();

        String sql = "from Resume as resume";

        String firstName = params.get(ResumeRequestParam.FIRST_NAME) != null ? params.get(ResumeRequestParam.FIRST_NAME).toString() : null;
        String lastName = params.get(ResumeRequestParam.LAST_NAME) != null ? params.get(ResumeRequestParam.LAST_NAME).toString() : null;
        String middleName = params.get(ResumeRequestParam.MIDDLE_NAME) != null ? params.get(ResumeRequestParam.MIDDLE_NAME).toString() : null;
        String profession = params.get(ResumeRequestParam.PROFESSION) != null ? params.get(ResumeRequestParam.PROFESSION).toString() : null;
        BigDecimal wage = params.get(ResumeRequestParam.WAGE) != null ? (BigDecimal) params.get(ResumeRequestParam.WAGE) : null;
        Long profFieldId = params.get(ResumeRequestParam.PROF_FIELD_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.PROF_FIELD_ID).toString()) : null;
        Long educationId = params.get(ResumeRequestParam.EDUCATION_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.EDUCATION_ID).toString()) : null;
        Long workTypeId = params.get(ResumeRequestParam.WORK_TYPE_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.WORK_TYPE_ID).toString()) : null;
        Long resumeStateId = params.get(ResumeRequestParam.RESUME_STATE_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.RESUME_STATE_ID).toString()) : null;

        List<Resume> list;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            if (lastName != null) sql += " "+separator.get()+" resume.profile.lastName like :lastName";
            if (firstName != null) sql += " "+separator.get()+" resume.profile.firstName like :firstName";
            if (middleName != null) sql += " "+separator.get()+" resume.profile.middleName like :middleName";
            if (profession != null) sql += " "+separator.get()+" resume.profession like :profession";
            if (profFieldId != null) sql += " "+separator.get()+" resume.profFieldId = :profFieldId";
            if (educationId != null) sql += " "+separator.get()+" resume.educationId = :educationId";
            if (workTypeId != null) sql += " "+separator.get()+" resume.workTypeId = :workTypeId";
            if (wage != null) sql += " "+separator.get()+" resume.wage.count = :wage";
            if (resumeStateId != null) sql += " "+separator.get()+" resume.state.id = :resumeStateId";

            Query query = session.createQuery(sql, Resume.class);

            if (lastName != null) query.setParameter("lastName", lastName);
            if (firstName != null) query.setParameter("firstName", firstName);
            if (profession != null) query.setParameter("profession", profession);
            if (middleName != null) query.setParameter("middleName", middleName);
            if (profFieldId != null) query.setParameter("profFieldId", profFieldId);
            if (educationId != null) query.setParameter("educationId", educationId);
            if (workTypeId != null) query.setParameter("workTypeId", workTypeId);
            if (wage != null) query.setParameter("wage", wage);
            if (resumeStateId != null) query.setParameter("resumeStateId", resumeStateId);

            list = query.getResultList();

            session.flush();

            transaction.commit();
        }
        return list;
    }

    @Override
    public Resume find(Long id) {
        Resume resume;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            resume = session.get(Resume.class, id);
            session.flush();
            transaction.commit();
        }
        return resume;
    }

    @Override
    public Resume create(Resume resume) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(resume);
            session.flush();
            transaction.commit();
        }
        return resume;
    }

    @Override
    public Resume update(Resume obj) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(obj);
            session.flush();
            transaction.commit();
        }
        return obj;
    }

    @Override
    public void delete(Resume resume) {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(resume);
            session.flush();
            transaction.commit();
        }
    }

    public static ResumeDao getInstance() {
        ResumeDao localInstance = instance;
        if (localInstance == null) {
            synchronized (ResumeDao.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ResumeDao();
                }
            }
        }
        return localInstance;
    }
}
