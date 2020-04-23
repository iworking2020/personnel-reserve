package ru.iworking.personnel.reserve.entity;

import ru.iworking.money.api.model.ICurrency;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "currency")
public class Currency implements ICurrency {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_to_system")
    private String nameToSystem;

    @ElementCollection
    @CollectionTable(name="currency_names_to_view", joinColumns = @JoinColumn(name="currency_id"))
    @Column(name="names_to_view")
    @MapKeyColumn(name="names_to_view_key")
    private Map<Locale, String> namesToView = new HashMap<>();

    public Currency() {}

    @Override
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
        Currency currency = (Currency) o;
        return Objects.equals(nameToSystem, currency.nameToSystem) &&
                Objects.equals(namesToView, currency.namesToView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameToSystem, namesToView);
    }
}
