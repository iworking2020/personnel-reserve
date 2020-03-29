package ru.iworking.personnel.reserve.entity;

import ru.iworking.service.api.model.IAddress;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address implements IAddress {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "country_id")
    private Long countryId;
    @Column(name = "region_id")
    private Long regionId;
    @Column(name = "city_id")
    private Long cityId;
    @Column(name = "street")
    private String street;
    @Column(name = "house")
    private String house;

    public Address() { }

    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getCountryId() {
        return countryId;
    }
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    @Override
    public Long getRegionId() {
        return regionId;
    }
    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Override
    public Long getCityId() {
        return cityId;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Override
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String getHouse() {
        return house;
    }
    public void setHouse(String house) {
        this.house = house;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(countryId, address.countryId) &&
                Objects.equals(regionId, address.regionId) &&
                Objects.equals(cityId, address.cityId) &&
                Objects.equals(street, address.street) &&
                Objects.equals(house, address.house);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, regionId, cityId, street, house);
    }
}
