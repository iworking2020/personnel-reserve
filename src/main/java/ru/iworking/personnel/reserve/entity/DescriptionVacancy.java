package ru.iworking.personnel.reserve.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "description_vacancy")
public class DescriptionVacancy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "about")
    private String about;

    @Column(name = "responsibilities")
    private String responsibilities;

    @Column(name = "requirements")
    private String requirements;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "contact")
    private String contact;

    public DescriptionVacancy() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }

    public String getResponsibilities() {
        return responsibilities;
    }
    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getRequirements() {
        return requirements;
    }
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getConditions() {
        return conditions;
    }
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DescriptionVacancy that = (DescriptionVacancy) o;
        return Objects.equals(about, that.about) &&
                Objects.equals(responsibilities, that.responsibilities) &&
                Objects.equals(requirements, that.requirements) &&
                Objects.equals(conditions, that.conditions) &&
                Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(about, responsibilities, requirements, conditions, contact);
    }
}
