package ru.iworking.personnel.reserve.entity;

import ru.iworking.vacancy.api.model.IVacancy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "vacancy")
public class Vacancy implements IVacancy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "datecreate")
    private LocalDateTime datecreate = LocalDateTime.now();

    @Column(name = "profession")
    private String profession;

    @Column(name = "prof_field_id")
    private Long profFieldId;

    @Column(name = "work_type_id")
    private Long workTypeId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="wage_id")
    private Wage wage;

    @Column(name="education_id")
    private Long educationId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="experience_id")
    private Experience experience;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="description_id")
    private DescriptionVacancy description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="address_id")
    private Address address;

    public Vacancy() { }

    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public Long getCompanyId() {
        return companyId;
    }
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public LocalDateTime getDatecreate() {
        return datecreate;
    }
    public void setDatecreate(LocalDateTime datecreate) {
        this.datecreate = datecreate;
    }

    @Override
    public String getProfession() {
        return profession;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public Long getProfFieldId() {
        return profFieldId;
    }
    public void setProfFieldId(Long profFieldId) {
        this.profFieldId = profFieldId;
    }

    @Override
    public Long getWorkTypeId() {
        return workTypeId;
    }
    public void setWorkTypeId(Long workTypeId) {
        this.workTypeId = workTypeId;
    }

    @Override
    public Wage getWage() {
        return wage;
    }
    public void setWage(Wage wage) {
        this.wage = wage;
    }

    @Override
    public Long getEducationId() {
        return educationId;
    }
    public void setEducationId(Long educationId) {
        this.educationId = educationId;
    }

    @Override
    public Experience getExperience() {
        return experience;
    }
    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    @Override
    public DescriptionVacancy getDescription() {
        return description;
    }
    public void setDescription(DescriptionVacancy description) {
        this.description = description;
    }

    @Override
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(userId, vacancy.userId) &&
                Objects.equals(companyId, vacancy.companyId) &&
                Objects.equals(datecreate, vacancy.datecreate) &&
                Objects.equals(profession, vacancy.profession) &&
                Objects.equals(profFieldId, vacancy.profFieldId) &&
                Objects.equals(workTypeId, vacancy.workTypeId) &&
                Objects.equals(wage, vacancy.wage) &&
                Objects.equals(educationId, vacancy.educationId) &&
                Objects.equals(experience, vacancy.experience) &&
                Objects.equals(description, vacancy.description) &&
                Objects.equals(address, vacancy.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, companyId, datecreate, profession, profFieldId, workTypeId, wage, educationId, experience, description, address);
    }
}
