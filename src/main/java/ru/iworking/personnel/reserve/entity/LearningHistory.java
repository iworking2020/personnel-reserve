package ru.iworking.personnel.reserve.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "LEARNING_HISTORY")
public class LearningHistory {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEARNING_HISTORY_SEQ_GEN")
    @SequenceGenerator(name = "LEARNING_HISTORY_SEQ_GEN", sequenceName = "LEARNING_HISTORY_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Education education;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

}
