package ru.iworking.personnel.reserve.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.joda.time.LocalDate;
import ru.iworking.personnel.reserve.converter.LocalDateDeserializer;
import ru.iworking.personnel.reserve.converter.LocalDateSerializer;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "EXPERIENCE_HISTORY")
public class ExperienceHistory {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXPERIENCE_HISTORY_SEQ_GEN")
    @SequenceGenerator(name = "EXPERIENCE_HISTORY_SEQ_GEN", sequenceName = "EXPERIENCE_HISTORY_SEQ", initialValue = 1000, allocationSize = 1)
    @EqualsAndHashCode.Exclude
    @Column(name = "ID")
    private Long id;

    @JsonSerialize(converter = LocalDateSerializer.class)
    @JsonDeserialize(converter = LocalDateDeserializer.class)
    @Column(name = "DATE_START")
    private LocalDate dateStart;

    @JsonSerialize(converter = LocalDateSerializer.class)
    @JsonDeserialize(converter = LocalDateDeserializer.class)
    @Column(name = "DATE_END")
    private LocalDate dateEnd;

    @Column(name = "DESCRIPTION")
    private String description;

}
