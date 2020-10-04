package ru.iworking.personnel.reserve.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "wage")
public class Wage {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WAGE_SEQ_GEN")
    @SequenceGenerator(name = "WAGE_SEQ_GEN", sequenceName = "WAGE_SEQ", initialValue = 1000, allocationSize = 1)
    @Column(name = "ID")
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "COUNT")
    private BigDecimal count;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CURRENCY_ID")
    private Currency currency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERIOD_ID")
    private Period period;

    public Integer getCount() {
        return Integer.valueOf(Double.valueOf(count.toString()).intValue());
    }
    public BigDecimal getCountBigDecimal() {
        return count;
    }

}
