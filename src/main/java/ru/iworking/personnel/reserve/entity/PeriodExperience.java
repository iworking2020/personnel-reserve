package ru.iworking.personnel.reserve.entity;

import lombok.*;
import ru.iworking.personnel.reserve.entity.name.NameSystem;
import ru.iworking.personnel.reserve.entity.name.NameView;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "PERIOD_EXPERIENCE")
public class PeriodExperience {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERIOD_EXPERIENCE_SEQ_GEN")
    @SequenceGenerator(name = "PERIOD_EXPERIENCE_SEQ_GEN", sequenceName = "PERIOD_EXPERIENCE_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "IS_DEFAULT", columnDefinition = "boolean default false")
    private Boolean isDefault = false;

    @Embedded private NameSystem nameSystem;
    @Embedded private NameView nameView;

}
