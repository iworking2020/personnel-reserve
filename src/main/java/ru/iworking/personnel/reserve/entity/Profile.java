package ru.iworking.personnel.reserve.entity;

import lombok.*;
import ru.iworking.personnel.reserve.utils.TimeUtil;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "PROFILE")
public class Profile {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILE_SEQ_GEN")
    @SequenceGenerator(name = "PROFILE_SEQ_GEN", sequenceName = "PROFILE_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "GENDER_ID")
    private Long genderId;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL, targetEntity = NumberPhone.class)
    @JoinColumn(name = "NUMBER_PHONE_ID")
    private NumberPhone numberPhone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DATE_BIRTHDAY")
    private LocalDate dateBirthday;

    public String getFullName() {
        return this.getLastName() + " " + this.getFirstName() + " " + this.getMiddleName();
    }

    public Integer getAge() {
        return TimeUtil.calAge(this.getDateBirthday(), null);
    }

}
