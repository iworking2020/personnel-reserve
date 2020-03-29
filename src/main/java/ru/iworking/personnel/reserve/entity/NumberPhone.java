package ru.iworking.personnel.reserve.entity;

import ru.iworking.service.api.model.INumberPhone;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "number_phone")
public class NumberPhone implements INumberPhone {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "number")
    private String number;

    public NumberPhone() { }

    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberPhone that = (NumberPhone) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, number);
    }

    @Override
    public String toString() {
        return "NumberPhone{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
