package ru.iworking.personnel.reserve.entity;

import ru.iworking.resume.api.model.IResume;
import ru.iworking.service.api.model.IDescription;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "resume")
public class Resume implements IResume, Cloneable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "datecreate")
    private LocalDateTime datecreate = LocalDateTime.now();

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="profile_id")
    private Profile profile;
    
    @Column(name = "profession")
    private String profession;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="number_phone_id")
    private NumberPhone numberPhone;
    
    @Column(name = "email")
    private String email;
    
    @Column(name="prof_field_id")
    private Long profFieldId;
    
    @Column(name="work_type_id")
    private Long workTypeId;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="wage_id")
    private Wage wage;
    
    @Column(name="education_id")
    private Long educationId;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="experience_id")
    private Experience experience;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;

    @Column(name = "photo_id")
    private Long photoId;

    public Resume() { }

    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public LocalDateTime getDatecreate() {
        return datecreate;
    }
    public void setDatecreate(LocalDateTime datecreate) {
        this.datecreate = datecreate;
    }

    public Profile getProfile() {
        return profile;
    }
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String getProfession() {
        return profession;
    }
    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public NumberPhone getNumberPhone() {
        return numberPhone;
    }
    public void setNumberPhone(NumberPhone numberPhone) {
        this.numberPhone = numberPhone;
    }

    @Override
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Long getProfFieldId() {
        return profFieldId;
    }
    public void setProfFieldId(Long profFieldId) {
        this.profFieldId = profFieldId;
    }

    @Override
    public Long getWorkTypeId() {
        return workTypeId;
    }
    public void setWorkTypeId(Long workTypeId) {
        this.workTypeId = workTypeId;
    }

    @Override
    public Wage getWage() {
        return wage;
    }
    public void setWage(Wage wage) {
        this.wage = wage;
    }

    @Override
    public Long getEducationId() {
        return educationId;
    }
    public void setEducationId(Long educationId) {
        this.educationId = educationId;
    }

    @Override
    public Experience getExperience() {
        return experience;
    }
    public void setExperience(Experience experience) {
        this.experience = experience;
    }

    @Override
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public Long getPhotoId() {
        return photoId;
    }
    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    @Override
    public IDescription getDescription() {
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(userId, resume.userId) &&
                Objects.equals(datecreate, resume.datecreate) &&
                Objects.equals(profile, resume.profile) &&
                Objects.equals(profession, resume.profession) &&
                Objects.equals(numberPhone, resume.numberPhone) &&
                Objects.equals(email, resume.email) &&
                Objects.equals(profFieldId, resume.profFieldId) &&
                Objects.equals(workTypeId, resume.workTypeId) &&
                Objects.equals(wage, resume.wage) &&
                Objects.equals(educationId, resume.educationId) &&
                Objects.equals(experience, resume.experience) &&
                Objects.equals(address, resume.address) &&
                Objects.equals(photoId, resume.photoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, datecreate, profile, profession, numberPhone, email, profFieldId, workTypeId, wage, educationId, experience, address, photoId);
    }
}
