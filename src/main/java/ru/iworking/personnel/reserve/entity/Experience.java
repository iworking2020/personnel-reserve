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
@Table(name = "EXPERIENCE")
public class Experience {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPERIENCE_SEQ_GEN")
    @SequenceGenerator(name = "EXPERIENCE_SEQ_GEN", sequenceName = "EXPERIENCE_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "DATE_START")
    private LocalDate dateStart;

    @Column(name = "DATE_END")
    private LocalDate dateEnd;
    
}