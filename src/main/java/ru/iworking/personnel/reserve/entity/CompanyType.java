package ru.iworking.personnel.reserve.entity;

import ru.iworking.company.api.model.ICompanyType;
import ru.iworking.service.api.enums.FinancialType;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Entity(name = "CompanyType")
@Table(name = "company_type")
public class CompanyType implements ICompanyType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_to_system")
    private String nameToSystem;

    @Column(name = "financial_type")
    private FinancialType financialType;

    @ElementCollection
    @CollectionTable(name="company_type_abbreviated_names_to_view", joinColumns = @JoinColumn(name="company_type_id"))
    @Column(name="abbreviated_names_to_view")
    @MapKeyColumn(name="abbreviated_names_to_view_key")
    private Map<Locale, String> abbreviatedNamesToView = new HashMap<>();

    @ElementCollection
    @CollectionTable(name="company_type_names_to_view", joinColumns = @JoinColumn(name="company_type_id"))
    @Column(name="names_to_view")
    @MapKeyColumn(name="names_to_view_key")
    private Map<Locale, String> namesToView = new HashMap<>();

    public CompanyType() { }

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
    public FinancialType getFinancialType() {
        return financialType;
    }
    public void setFinancialType(FinancialType financialType) {
        this.financialType = financialType;
    }

    @Override
    public Map<Locale, String> getAbbreviatedNamesToView() {
        return abbreviatedNamesToView;
    }
    public void setAbbreviatedNamesToView(Map<Locale, String> abbreviatedNamesToView) {
        this.abbreviatedNamesToView = abbreviatedNamesToView;
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
        CompanyType that = (CompanyType) o;
        return Objects.equals(nameToSystem, that.nameToSystem) &&
                financialType == that.financialType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameToSystem, financialType);
    }
}
