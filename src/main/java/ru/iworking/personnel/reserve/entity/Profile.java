package ru.iworking.personnel.reserve.entity;

import ru.iworking.auth.api.model.IProfile;
import ru.iworking.service.api.model.INumberPhone;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "profile")
public class Profile implements IProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "gender_id")
    private Long genderId;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, targetEntity = NumberPhone.class)
    @JoinColumn(name = "number_phone_id")
    private NumberPhone numberPhone;

    @Column(name = "email")
    private String email;

    private LocalDate dateBirthday;

    public Profile() { }

    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    @Override
    public INumberPhone getNumberPhone() {
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
    public LocalDate getDateBirthday() {
        return dateBirthday;
    }
    public void setDateBirthday(LocalDate dateBirthday) {
        this.dateBirthday = dateBirthday;
    }

    @Override
    public Long getAvatarId() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(lastName, profile.lastName) &&
                Objects.equals(firstName, profile.firstName) &&
                Objects.equals(middleName, profile.middleName) &&
                Objects.equals(genderId, profile.genderId) &&
                Objects.equals(numberPhone, profile.numberPhone) &&
                Objects.equals(email, profile.email) &&
                Objects.equals(dateBirthday, profile.dateBirthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, middleName, genderId, numberPhone, email, dateBirthday);
    }
}
