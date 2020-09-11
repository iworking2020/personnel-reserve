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
@Table(name = "EDUCATION")
public class Education {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EDUCATION_SEQ_GEN")
    @SequenceGenerator(name = "EDUCATION_SEQ_GEN", sequenceName = "EDUCATION_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Embedded private NameSystem nameSystem;
    @Embedded private NameView nameView;

}
