package ru.iworking.personnel.reserve.entity;

import ru.iworking.personnel.reserve.model.State;
import ru.iworking.service.api.model.NameToSystem;
import ru.iworking.service.api.model.NameToView;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "resume_state")
public class ResumeState implements NameToSystem, NameToView, State {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_to_system")
    private String nameToSystem;

    @ElementCollection
    @CollectionTable(name="resume_state_names_to_view", joinColumns = @JoinColumn(name="resume_state_id"))
    @Column(name="names_to_view")
    @MapKeyColumn(name="names_to_view_key")
    private Map<Locale, String> namesToView = new HashMap<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getNameToSystem() {
        return nameToSystem;
    }
    public void setNameToSystem(String nameToSystem) {
        this.nameToSystem = nameToSystem;
    }

    @Override
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
        ResumeState that = (ResumeState) o;
        return Objects.equals(nameToSystem, that.nameToSystem) &&
                Objects.equals(namesToView, that.namesToView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameToSystem, namesToView);
    }

}
