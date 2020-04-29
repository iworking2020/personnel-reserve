package ru.iworking.personnel.reserve.entity;

import ru.iworking.personnel.reserve.entity.name.NameSystem;
import ru.iworking.personnel.reserve.entity.name.NameView;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "currency")
public class Currency {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded private NameSystem nameSystem;
    @Embedded private NameView nameView;

    public Currency() {}

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
        Currency currency = (Currency) o;
        return Objects.equals(nameSystem, currency.nameSystem) &&
                Objects.equals(nameView, currency.nameView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameSystem, nameView);
    }
}
