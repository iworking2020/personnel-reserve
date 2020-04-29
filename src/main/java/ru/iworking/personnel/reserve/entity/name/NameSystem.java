package ru.iworking.personnel.reserve.entity.name;

import ru.iworking.personnel.reserve.interfaces.name.INameSystem;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class NameSystem implements INameSystem, Serializable {

    @Column(name = "name_system")
    protected String nameSystem;

    public NameSystem() { }

    @Override
    public String getName() {
        return nameSystem;
    }
    public void setName(String name) {
        this.nameSystem = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NameSystem that = (NameSystem) o;
        return Objects.equals(nameSystem, that.nameSystem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameSystem);
    }

    @Override
    public String toString() {
        return nameSystem;
    }
}