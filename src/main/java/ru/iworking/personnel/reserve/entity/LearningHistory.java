package ru.iworking.personnel.reserve.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class LearningHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Education education;

    @Lob
    private String description;

    public LearningHistory() { }

    public LearningHistory(Education education, String description) {
        this.education = education;
        this.description = description;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Education getEducation() {
        return education;
    }
    public void setEducation(Education education) {
        this.education = education;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningHistory that = (LearningHistory) o;
        return Objects.equals(education, that.education) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(education, description);
    }
}
