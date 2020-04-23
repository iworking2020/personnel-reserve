package ru.iworking.personnel.reserve.entity;

import org.springframework.format.annotation.DateTimeFormat;
import ru.iworking.service.api.model.IExperience;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "experience")
public class Experience implements IExperience {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "date_start")
    private LocalDate dateStart;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "date_end")
    private LocalDate dateEnd;

    public Experience() {}

    public Experience(LocalDate dateStart, LocalDate dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    @Override
    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return Objects.equals(dateStart, that.dateStart) &&
                Objects.equals(dateEnd, that.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateStart, dateEnd);
    }
}