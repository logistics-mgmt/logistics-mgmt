package com.jdbc.demo.domain;

/**
 * Created by Mateusz on 22-Oct-15.
 */
public class Address {


    private int id;
    private String town;
    private String street;
    private String code;
    private String houseNumber;
    private String country;

    public Address(){
        
    }

    public Address(int id, String town, String street, String code, String houseNumber, String country) {
        this.id = id;
        this.town = town;
        this.street = street;
        this.code = code;
        this.houseNumber = houseNumber;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (id != address.id) return false;
        if (!town.equals(address.town)) return false;
        if (!street.equals(address.street)) return false;
        if (!code.equals(address.code)) return false;
        if (!houseNumber.equals(address.houseNumber)) return false;
        return country.equals(address.country);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + town.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + houseNumber.hashCode();
        result = 31 * result + country.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", town='" + town + '\'' +
                ", street='" + street + '\'' +
                ", code='" + code + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code.trim();
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
