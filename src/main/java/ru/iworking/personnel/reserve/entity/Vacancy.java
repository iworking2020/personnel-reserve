package ru.iworking.personnel.reserve.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(name = "COMPANY_ID")
    private Long companyId;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="EXPERIENCE_ID")
    private Experience experience;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "DESCRIPTION_VACANCY_ID")
    private DescriptionVacancy description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="ADDRESS_ID")
    private Address address;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "vacancy")
    @EqualsAndHashCode.Exclude
    private Set<Click> clicks;

    @Column(name = "IMAGE_CONTAINER_ID")
    private Long photoId;

}
