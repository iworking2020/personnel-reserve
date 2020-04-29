package ru.iworking.personnel.reserve.entity;

import ru.iworking.personnel.reserve.entity.name.NameSystem;
import ru.iworking.personnel.reserve.entity.name.NameView;
import ru.iworking.personnel.reserve.interfaces.State;
import ru.iworking.service.api.model.NameToSystem;
import ru.iworking.service.api.model.NameToView;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "resume_state")
public class ResumeState implements State {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded private NameSystem nameSystem;
    @Embedded private NameView nameView;

    @Override
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
        ResumeState that = (ResumeState) o;
        return Objects.equals(nameSystem, that.nameSystem) &&
                Objects.equals(nameView, that.nameView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameSystem, nameView);
    }
}
