package ru.iworking.personnel.reserve.entity;

import java.math.BigDecimal;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "resume")
public class Resume {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "profession")
    private String profession;
    
    @Column(name = "number_phone")
    private String numberPhone;
    
    @Column(name = "email")
    private String email;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="prof_field_id")
    private ProfField profField;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="work_type_id")
    private WorkType workType;
    
    @Column(name = "wage")
    private BigDecimal wage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="currency_id")
    private Currency currency;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="education_id")
    private Education education;
    
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="experience_id")
    private Experience experience;
    
    @Column(name = "address")
    private String address;

    public Resume() { }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getProfession() {
        return profession;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNumberPhone() {
        return numberPhone;
    }
    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public ProfField getProfField() {
        return profField;
    }
    public void setProfField(ProfField profField) {
        this.profField = profField;
    }

    public WorkType getWorkType() {
        return workType;
    }
    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public BigDecimal getWage() {
        return wage;
    }
    public void setWage(BigDecimal wage) {
        this.wage = wage;
    }

    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Education getEducation() {
        return education;
    }
    public void setEducation(Education education) {
        this.education = education;
    }

    public Experience getExperience() {
        return experience;
    }
    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.lastName);
        hash = 71 * hash + Objects.hashCode(this.firstName);
        hash = 71 * hash + Objects.hashCode(this.middleName);
        hash = 71 * hash + Objects.hashCode(this.profession);
        hash = 71 * hash + Objects.hashCode(this.numberPhone);
        hash = 71 * hash + Objects.hashCode(this.email);
        hash = 71 * hash + Objects.hashCode(this.profField);
        hash = 71 * hash + Objects.hashCode(this.workType);
        hash = 71 * hash + Objects.hashCode(this.wage);
        hash = 71 * hash + Objects.hashCode(this.currency);
        hash = 71 * hash + Objects.hashCode(this.education);
        hash = 71 * hash + Objects.hashCode(this.experience);
        hash = 71 * hash + Objects.hashCode(this.address);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Resume other = (Resume) obj;
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.middleName, other.middleName)) {
            return false;
        }
        if (!Objects.equals(this.profession, other.profession)) {
            return false;
        }
        if (!Objects.equals(this.numberPhone, other.numberPhone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.wage, other.wage)) {
            return false;
        }
        if (!Objects.equals(this.currency, other.currency)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.profField, other.profField)) {
            return false;
        }
        if (!Objects.equals(this.workType, other.workType)) {
            return false;
        }
        if (!Objects.equals(this.education, other.education)) {
            return false;
        }
        if (!Objects.equals(this.experience, other.experience)) {
            return false;
        }
        return true;
    }

}
