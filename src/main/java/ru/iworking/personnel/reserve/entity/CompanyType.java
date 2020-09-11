package ru.iworking.personnel.reserve.entity;

import lombok.*;
import ru.iworking.personnel.reserve.entity.name.AbbreviatedNameView;
import ru.iworking.personnel.reserve.entity.name.NameSystem;
import ru.iworking.personnel.reserve.entity.name.NameView;
import ru.iworking.personnel.reserve.enums.FinancialType;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "COMPANY_TYPE")
public class CompanyType {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_TYPE_SEQ_GEN")
    @SequenceGenerator(name = "COMPANY_TYPE_SEQ_GEN", sequenceName = "COMPANY_TYPE_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "FINANCIAL_TYPE")
    private FinancialType financialType;

    @Embedded private NameSystem nameSystem;
    @Embedded private NameView nameView;
    @Embedded private AbbreviatedNameView abbreviatedNameView;

}
