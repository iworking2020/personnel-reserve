package ru.iworking.personnel.reserve.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

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
    private BigDecimal originalCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CURRENCY_ID")
    private Currency currency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERIOD_ID")
    private Period period;

    @JsonIgnore
    public Integer getCount() {
        if (Objects.nonNull(originalCount)) {
            return Integer.valueOf(Double.valueOf(originalCount.toString()).intValue());
        } else {
            return null;
        }
    }
    @JsonIgnore
    public BigDecimal getCountBigDecimal() {
        return originalCount;
    }

}
