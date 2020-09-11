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
@Table(name = "PROF_FIELD")
public class ProfField {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROF_FIELD_SEQ_GEN")
    @SequenceGenerator(name = "PROF_FIELD_SEQ_GEN", sequenceName = "PROF_FIELD_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Embedded private NameSystem nameSystem;
    @Embedded private NameView nameView;

}