package ru.iworking.personnel.reserve.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "NUMBER_PHONE")
public class NumberPhone {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NUMBER_PHONE_SEQ_GEN")
    @SequenceGenerator(name = "NUMBER_PHONE_SEQ_GEN", sequenceName = "NUMBER_PHONE_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NUMBER")
    private String number;

    public String getFullNumber() {
        return this.getCode() + this.getNumber();
    }

}
