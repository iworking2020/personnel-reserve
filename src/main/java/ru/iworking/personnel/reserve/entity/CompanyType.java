package ru.iworking.personnel.reserve.entity;

import ru.iworking.personnel.reserve.entity.name.AbbreviatedNameView;
import ru.iworking.personnel.reserve.entity.name.NameSystem;
import ru.iworking.personnel.reserve.entity.name.NameView;
import ru.iworking.service.api.enums.FinancialType;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Entity(name = "CompanyType")
@Table(name = "company_type")
public class CompanyType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "financial_type")
    private FinancialType financialType;

    @Embedded private NameSystem nameSystem;
    @Embedded private NameView nameView;
    @Embedded private AbbreviatedNameView abbreviatedNameView;

    public CompanyType() { }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public FinancialType getFinancialType() {
        return financialType;
    }
    public void setFinancialType(FinancialType financialType) {
        this.financialType = financialType;
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

    public AbbreviatedNameView getAbbreviatedNameView() {
        return abbreviatedNameView;
    }
    public void setAbbreviatedNameView(AbbreviatedNameView abbreviatedNameView) {
        this.abbreviatedNameView = abbreviatedNameView;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyType that = (CompanyType) o;
        return financialType == that.financialType &&
                Objects.equals(nameSystem, that.nameSystem) &&
                Objects.equals(nameView, that.nameView) &&
                Objects.equals(abbreviatedNameView, that.abbreviatedNameView);
    }

    @Override
    public int hashCode() {
        return Objects.hash(financialType, nameSystem, nameView, abbreviatedNameView);
    }
}
