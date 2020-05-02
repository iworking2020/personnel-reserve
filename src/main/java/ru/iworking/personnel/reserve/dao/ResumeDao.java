package ru.iworking.personnel.reserve.dao;

import org.hibernate.Session;
import ru.iworking.personnel.reserve.entity.Resume;
import ru.iworking.personnel.reserve.props.ResumeRequestParam;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * public List<Resume> findAllFromCash(Map<String, Object> params) {
 *         String firstName = params.get(ResumeRequestParam.FIRST_NAME) != null ? params.get(ResumeRequestParam.FIRST_NAME).toString() : null;
 *         String lastName = params.get(ResumeRequestParam.LAST_NAME) != null ? params.get(ResumeRequestParam.LAST_NAME).toString() : null;
 *         String middleName = params.get(ResumeRequestParam.MIDDLE_NAME) != null ? params.get(ResumeRequestParam.MIDDLE_NAME).toString() : null;
 *         String profession = params.get(ResumeRequestParam.PROFESSION) != null ? params.get(ResumeRequestParam.PROFESSION).toString() : null;
 *         BigDecimal wage = params.get(ResumeRequestParam.WAGE) != null ? (BigDecimal) params.get(ResumeRequestParam.WAGE) : null;
 *         Long profFieldId = params.get(ResumeRequestParam.PROF_FIELD_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.PROF_FIELD_ID).toString()) : null;
 *         Long educationId = params.get(ResumeRequestParam.EDUCATION_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.EDUCATION_ID).toString()) : null;
 *         Long workTypeId = params.get(ResumeRequestParam.WORK_TYPE_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.WORK_TYPE_ID).toString()) : null;
 *         Long resumeStateId = params.get(ResumeRequestParam.RESUME_STATE_ID) != null ? Long.valueOf(params.get(ResumeRequestParam.RESUME_STATE_ID).toString()) : null;
 *
 *         return findAllFromCache().stream()
 *                 .filter(resume -> firstName == null ? true : resume.getProfile() != null && resume.getProfile().getFirstName().equals(firstName))
 *                 .filter(resume -> lastName == null ? true : resume.getProfile() != null && resume.getProfile().getLastName().equals(lastName))
 *                 .filter(resume -> middleName == null ? true : resume.getProfile() != null && resume.getProfile().getMiddleName().equals(middleName))
 *                 .filter(resume -> profession == null ? true : resume.getProfession().equals(profession))
 *                 .filter(resume -> wage == null ? true : resume.getWage() != null && resume.getWage().getCountBigDecimal().equals(wage))
 *                 .filter(resume -> profFieldId == null ? true : resume.getProfFieldId() == profFieldId)
 *                 .filter(resume -> educationId == null ? true : resume.getEducationId() == educationId)
 *                 .filter(resume -> workTypeId == null ? true : resume.getWorkTypeId() == workTypeId)
 *                 .filter(resume -> resumeStateId == null ? true : resume.getState() != null && resume.getState().getId() == resumeStateId)
 *                 .collect(Collectors.toList());
 *     }
 */
public class ResumeDao extends Dao<Resume, Long> {

    public static final ResumeDao INSTANCE = new ResumeDao();

    private ResumeDao() {}

    @Override
    public Resume findById(Long id) {
        Resume resume = (Resume) getSessionProvider().getCurrentSession().get(Resume.class, id);
        return resume;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Resume> findAll() {
        List<Resume> books = (List<Resume>) getSessionProvider().getCurrentSession().createQuery("from Resume").list();
        return books;
    }

    public Long count(Map<String, Object> params) {
        Long count = (Long) createQuery("select COUNT(resume) from Resume as resume where 1=1", params, Long.class).getSingleResult();
        return count;
    }

    @SuppressWarnings("unchecked")
    public List<Resume> findAll(Map<String, Object> params) {
        List<Resume> list = createQuery("from Resume as resume where 1=1", params, Resume.class).getResultList();
        return list;
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

        Session session = getSessionProvider().getCurrentSession();

        Query query = clazz != null ? session.createQuery(sql, clazz) : session.createQuery(sql);

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

}
