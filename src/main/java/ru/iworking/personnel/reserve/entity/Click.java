package ru.iworking.personnel.reserve.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Click implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Resume resume;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Vacancy vacancy;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="resume_state_id")
    private ResumeState state;

    public Click(Resume resume, Vacancy vacancy, ResumeState state) {
        this.resume = resume;
        this.vacancy = vacancy;
        this.state = state;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Resume getResume() {
        return resume;
    }
    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }
    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public ResumeState getState() {
        return state;
    }
    public void setState(ResumeState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Click click = (Click) o;
        return Objects.equals(resume, click.resume) &&
                Objects.equals(vacancy, click.vacancy) &&
                Objects.equals(state, click.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resume, vacancy, state);
    }
}
