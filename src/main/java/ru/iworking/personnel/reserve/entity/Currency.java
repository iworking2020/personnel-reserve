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
@Table(name = "CURRENCY")
public class Currency {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CURRENCY_SEQ_GEN")
    @SequenceGenerator(name = "CURRENCY_SEQ_GEN", sequenceName = "CURRENCY_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "id")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Embedded private NameSystem nameSystem;
    @Embedded private NameView nameView;

}
