package ru.iworking.personnel.reserve.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "COMPANY")
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_SEQ_GEN")
    @SequenceGenerator(name = "COMPANY_SEQ_GEN", sequenceName = "COMPANY_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE_CREATE")
    private LocalDateTime dateCreate = LocalDateTime.now();

    @Column(name = "COMPANY_TYPE_ID")
    private Long companyTypeId;

    @Column(name = "NAME")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="NUMBER_PHONE_ID")
    private NumberPhone numberPhone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "WEB_PAGE")
    private String webPage;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="ADDRESS_ID")
    private Address address;

    @Column(name = "CREATER_ID")
    private Long createrId;

    @Column(name = "IMAGE_CONTAINER_ID")
    private Long imageContainerId;

}
