package ru.iworking.personnel.reserve.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
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

    @Lob
    @Column(name = "photo")
    private byte[] photo;

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

    public byte[] getPhoto() {
        return photo;
    }
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(lastName, resume.lastName) &&
                Objects.equals(firstName, resume.firstName) &&
                Objects.equals(middleName, resume.middleName) &&
                Objects.equals(profession, resume.profession) &&
                Objects.equals(numberPhone, resume.numberPhone) &&
                Objects.equals(email, resume.email) &&
                Objects.equals(profField, resume.profField) &&
                Objects.equals(workType, resume.workType) &&
                Objects.equals(wage, resume.wage) &&
                Objects.equals(currency, resume.currency) &&
                Objects.equals(education, resume.education) &&
                Objects.equals(experience, resume.experience) &&
                Objects.equals(address, resume.address) &&
                Arrays.equals(photo, resume.photo);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(lastName, firstName, middleName, profession, numberPhone, email, profField, workType, wage, currency, education, experience, address);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
