package ru.iworking.personnel.reserve.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Click implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Resume resume;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Vacancy vacancy;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private ResumeState resumeState;

}
