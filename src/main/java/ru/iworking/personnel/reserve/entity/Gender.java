package ru.iworking.personnel.reserve.entity;

import ru.iworking.personnel.reserve.entity.name.NameSystem;
import ru.iworking.personnel.reserve.entity.name.NameView;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "gender")
public class Gender {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded private NameSystem nameSystem;
    @Embedded private NameView nameView;

    public Gender() { }

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
        Gender gender = (Gender) o;
        return Objects.equals(nameSystem, gender.nameSystem) &&
                Objects.equals(nameView, gender.nameView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameSystem, nameView);
    }
}
