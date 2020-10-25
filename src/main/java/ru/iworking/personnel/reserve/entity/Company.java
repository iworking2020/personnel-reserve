package ru.iworking.personnel.reserve.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.joda.time.LocalDateTime;
import ru.iworking.personnel.reserve.converter.LocalDateTimeDeserializer;
import ru.iworking.personnel.reserve.converter.LocalDateTimeSerializer;

import javax.persistence.*;

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

    @JsonSerialize(converter = LocalDateTimeSerializer.class)
    @JsonDeserialize(converter = LocalDateTimeDeserializer.class)
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

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="IMAGE_CONTAINER_ID")
    private ImageContainer logo;

}
