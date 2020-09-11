package ru.iworking.personnel.reserve.entity;

import lombok.*;
import ru.iworking.personnel.reserve.entity.name.NameSystem;
import ru.iworking.personnel.reserve.entity.name.NameView;
import ru.iworking.personnel.reserve.interfaces.State;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "RESUME_STATE")
public class ResumeState implements State {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESUME_STATE_SEQ_GEN")
    @SequenceGenerator(name = "RESUME_STATE_SEQ_GEN", sequenceName = "RESUME_STATE_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Embedded private NameSystem nameSystem;
    @Embedded private NameView nameView;

}
