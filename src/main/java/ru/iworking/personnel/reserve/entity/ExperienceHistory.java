package ru.iworking.personnel.reserve.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "EXPERIENCE_HISTORY")
public class ExperienceHistory {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPERIENCE_HISTORY_SEQ_GEN")
    @SequenceGenerator(name = "EXPERIENCE_HISTORY_SEQ_GEN", sequenceName = "EXPERIENCE_HISTORY_SEQ", initialValue = 1000, allocationSize = 1)
    @EqualsAndHashCode.Exclude
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE_START")
    private LocalDate dateStart;

    @Column(name = "DATE_END")
    private LocalDate dateEnd;

    @Column(name = "DESCRIPTION")
    private String description;

}
