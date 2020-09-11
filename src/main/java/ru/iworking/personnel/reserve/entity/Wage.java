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

    @Column(name = "count")
    private BigDecimal count;

    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "period_id")
    private Long periodId;

    public Integer getCount() {
        return Integer.valueOf(Double.valueOf(count.toString()).intValue());
    }
    public BigDecimal getCountBigDecimal() {
        return count;
    }

}
