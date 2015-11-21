package com.jdbc.demo.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 22-Oct-15.
 */
public class Driver {


    private int id;
    private Address address;
    private String firstName;
    private String lastName;
    private String PESEL;
    private BigDecimal salary;
    private BigDecimal salaryBonus;
    private Boolean available;
    private Boolean deleted;
    private ArrayList<FreightTransport> transports;

    public Driver(){

    }

    public Driver(int id, Address address, String firstName, String lastName, String PESEL, BigDecimal salary,
                  BigDecimal salaryBonus, Boolean available, Boolean deleted) {
        super();
        this.id = id;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.PESEL = PESEL;
        this.salary = salary;
        this.salaryBonus = salaryBonus;
        this.available = available;
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        if (id != driver.id) return false;
        if (!address.equals(driver.address)) return false;
        if (!firstName.equals(driver.firstName)) return false;
        if (!lastName.equals(driver.lastName)) return false;
        if (!PESEL.equals(driver.PESEL)) return false;
        if (salary != null ? !salary.equals(driver.salary) : driver.salary != null) return false;
        if (salaryBonus != null ? !salaryBonus.equals(driver.salaryBonus) : driver.salaryBonus != null) return false;
        if (available != null ? !available.equals(driver.available) : driver.available != null) return false;
        if (!((transports == null || driver.transports == null) || (transports.size() == driver.transports.size() && driver.transports.containsAll(transports))));
        return !(deleted != null ? !deleted.equals(driver.deleted) : driver.deleted != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + address.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + PESEL.hashCode();
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        result = 31 * result + (salaryBonus != null ? salaryBonus.hashCode() : 0);
        result = 31 * result + (available != null ? available.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", address=" + address +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", PESEL='" + PESEL + '\'' +
                ", salary=" + salary +
                ", salaryBonus=" + salaryBonus +
                ", available=" + available +
                ", deleted=" + deleted +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address idAddress) {
        this.address = idAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getSalaryBonus() {
        return salaryBonus;
    }

    public void setSalaryBonus(BigDecimal salaryBonus) {
        this.salaryBonus = salaryBonus;
    }

    public Boolean isAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<FreightTransport> getTransports() {
        return transports;
    }

    public void setTransports(List<FreightTransport> transports) {
        this.transports = (ArrayList<FreightTransport>) transports;
    }
}
