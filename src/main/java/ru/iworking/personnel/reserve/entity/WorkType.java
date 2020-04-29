package ru.iworking.personnel.reserve.entity;

import ru.iworking.personnel.reserve.entity.name.NameSystem;
import ru.iworking.personnel.reserve.entity.name.NameView;
import ru.iworking.profession.api.model.IWorkType;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "work_type")
public class WorkType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded private NameSystem nameSystem;
    @Embedded private NameView nameView;

    public WorkType() { }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public NameSystem getNameSystem() {
        return nameSystem;
    }
    public void setNameSystem(NameSystem nameSystem) {
        this.nameSystem = nameSystem;
    }

    public NameView getNameView() {
        return nameView;
    }
    public void setNameView(NameView nameView) {
        this.nameView = nameView;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkType workType = (WorkType) o;
        return Objects.equals(nameSystem, workType.nameSystem) &&
                Objects.equals(nameView, workType.nameView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameSystem, nameView);
    }
}
