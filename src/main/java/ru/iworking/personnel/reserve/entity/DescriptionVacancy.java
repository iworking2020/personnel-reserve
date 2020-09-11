package ru.iworking.personnel.reserve.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "DESCRIPTION_VACANCY")
public class DescriptionVacancy {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DESCRIPTION_VACANCY_SEQ_GEN")
    @SequenceGenerator(name = "DESCRIPTION_VACANCY_SEQ_GEN", sequenceName = "DESCRIPTION_VACANCY_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "ABOUT")
    private String about;

    @Column(name = "RESPONSIBILITIES")
    private String responsibilities;

    @Column(name = "REQUIREMENTS")
    private String requirements;

    @Column(name = "CONDITIONS")
    private String conditions;

    @Column(name = "CONTACT")
    private String contact;

}
