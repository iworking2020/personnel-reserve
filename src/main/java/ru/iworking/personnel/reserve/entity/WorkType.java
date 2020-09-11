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
@Table(name = "WORK_TYPE")
public class WorkType {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORK_TYPE_SEQ_GEN")
    @SequenceGenerator(name = "WORK_TYPE_SEQ_GEN", sequenceName = "WORK_TYPE_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Embedded private NameSystem nameSystem;
    @Embedded private NameView nameView;

}
