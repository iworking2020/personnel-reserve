package ru.iworking.personnel.reserve.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.joda.time.LocalDateTime;
import ru.iworking.personnel.reserve.converter.LocalDateTimeDeserializer;
import ru.iworking.personnel.reserve.converter.LocalDateTimeSerializer;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "VACANCY")
public class Vacancy {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VACANCY_SEQ_GEN")
    @SequenceGenerator(name = "VACANCY_SEQ_GEN", sequenceName = "VACANCY_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Company.class)
    @JoinTable(name="COMPANY_VACANCY",
        joinColumns={@JoinColumn(name="VACANCY_ID")},
        inverseJoinColumns={@JoinColumn(name="COMPANY_ID")})
    private Company company;

    @JsonSerialize(converter = LocalDateTimeSerializer.class)
    @JsonDeserialize(converter = LocalDateTimeDeserializer.class)
    @Column(name = "DATE_CREATE")
    private LocalDateTime dateCreate = LocalDateTime.now();

    @Column(name = "PROFESSION")
    private String profession;

    @Column(name = "PROF_FIELD_ID")
    private Long profFieldId;

    @Column(name = "WORK_TYPE_ID")
    private Long workTypeId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="WAGE_ID")
    private Wage wage;

    @Column(name = "EDUCATION_ID")
    private Long educationId;

    @Column(name = "MIN_EXPERIENCE")
    protected Integer minExperience;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERIOD_MIN_EXPERIENCE_ID")
    protected PeriodExperience periodMinExperience;

    @Column(name = "MAX_EXPERIENCE")
    protected Integer maxExperience;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERIOD_MAX_EXPERIENCE_ID")
    protected PeriodExperience periodMaxExperience;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "DESCRIPTION_VACANCY_ID")
    private DescriptionVacancy description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="ADDRESS_ID")
    private Address address;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vacancy")
    private Set<Click> clicks;

    @Column(name = "IMAGE_CONTAINER_ID")
    private Long photoId;

}
