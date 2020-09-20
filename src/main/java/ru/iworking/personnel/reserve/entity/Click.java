package ru.iworking.personnel.reserve.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "CLICK")
public class Click implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLICK_SEQ_GEN")
    @SequenceGenerator(name = "CLICK_SEQ_GEN", sequenceName = "CLICK_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RESUME_ID")
    private Resume resume;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VACANCY_ID")
    private Vacancy vacancy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RESUME_STATE_ID")
    private ResumeState resumeState;

}
