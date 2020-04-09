package ru.iworking.personnel.reserve.entity;

import ru.iworking.service.api.model.IWage;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "wage")
public class Wage implements IWage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "count")
    private BigDecimal count;

    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "period_id")
    private Long periodId;

    public Wage() { }

    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Integer getCount() {
        return Integer.valueOf(Double.valueOf(count.toString()).intValue());
    }
    public BigDecimal getCountBigDecimal() {
        return count;
    }
    public void setCount(BigDecimal count) {
        this.count = count;
    }

    @Override
    public Long getCurrencyId() {
        return currencyId;
    }
    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    @Override
    public Long getPeriodId() {
        return periodId;
    }
    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wage wage = (Wage) o;
        return Objects.equals(count, wage.count) &&
                Objects.equals(currencyId, wage.currencyId) &&
                Objects.equals(periodId, wage.periodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, currencyId, periodId);
    }
}
