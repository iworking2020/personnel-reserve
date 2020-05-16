package ru.iworking.personnel.reserve.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Vacancy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    private Long userId;

    private Long companyId;

    private LocalDateTime datecreate = LocalDateTime.now();

    private String profession;

    private Long profFieldId;

    private Long workTypeId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Wage wage;

    private Long educationId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Experience experience;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private DescriptionVacancy description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "vacancy")
    @EqualsAndHashCode.Exclude
    private Set<Click> clicks;

    private Long logoId;

}
