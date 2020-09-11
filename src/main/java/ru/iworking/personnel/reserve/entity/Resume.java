package ru.iworking.personnel.reserve.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "RESUME")
public class Resume implements Cloneable {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESUME_SEQ_GEN")
    @SequenceGenerator(name = "RESUME_SEQ_GEN", sequenceName = "RESUME_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "DATE_CREATE")
    private LocalDateTime dateCreate = LocalDateTime.now();

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="PROFILE_ID")
    private Profile profile;

    @Column(name = "PROFESSION")
    private String profession;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="NUMBER_PHONE_ID")
    private NumberPhone numberPhone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PROF_FIELD_ID")
    private Long profFieldId;

    @Column(name = "WORK_TYPE_ID")
    private Long workTypeId;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="WAGE_ID")
    private Wage wage;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="ADDRESS_ID")
    private Address address;

    @Column(name = "IMAGE_CONTAINER_ID")
    private Long photoId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LearningHistory> learningHistoryList;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dateStart ASC")
    private List<ExperienceHistory> experienceHistoryList;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "resume")
    private List<Click> clicks;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
