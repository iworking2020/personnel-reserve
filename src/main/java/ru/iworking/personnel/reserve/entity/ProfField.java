package ru.iworking.personnel.reserve.entity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "prof_field")
public class ProfField {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_to_system")
    private String nameToSystem;

    @ElementCollection
    @CollectionTable(name="prof_field_names_to_view", joinColumns = @JoinColumn(name="prof_field_id"))
    @Column(name="names_to_view")
    @MapKeyColumn(name="names_to_view_key")
    private Map<Locale, String> namesToView = new HashMap<>();

    public ProfField() { }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNameToSystem() {
        return nameToSystem;
    }
    public void setNameToSystem(String nameToSystem) {
        this.nameToSystem = nameToSystem;
    }

    public Map<Locale, String> getNamesToView() {
        return namesToView;
    }
    public void setNamesToView(Map<Locale, String> namesToView) {
        this.namesToView = namesToView;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfField profField = (ProfField) o;
        return Objects.equals(nameToSystem, profField.nameToSystem) &&
                Objects.equals(namesToView, profField.namesToView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameToSystem, namesToView);
    }
}