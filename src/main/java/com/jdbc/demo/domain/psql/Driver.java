package com.jdbc.demo.domain.psql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jdbc.demo.domain.schedule.ScheduleEvent;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mateusz on 22-Oct-15.
 */
@Entity
@JsonIgnoreProperties({"transports"})
@NamedNativeQueries({
        @NamedNativeQuery(name = "driver.all", query = "Select * from Driver", resultClass = Driver.class),
})
public class Driver {

    @Id
    @SequenceGenerator(sequenceName = "DRIVER_ID_SEQ", name = "DriverIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DriverIdSequence")
    @Column(name = "id_Driver")
    private long id;

    @ManyToOne
    @JoinColumn(name="id_Address")
    private Address address;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String PESEL;
    private BigDecimal salary;

    @Column(name = "salary_bonus")
    private BigDecimal salaryBonus;

    private Boolean available;
    private Boolean deleted;

    @ManyToMany
    @JoinTable(name="FreightTransportDrivers", joinColumns = { @JoinColumn(name="id_Driver") },
            inverseJoinColumns = { @JoinColumn(name="id_FreightTransport") })
    private List<FreightTransport> transports = new ArrayList<>();

    public Driver(){

    }

    public Driver(long id, Address address, String firstName, String lastName, String PESEL, BigDecimal salary,
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
        if (!((transports == null || driver.transports == null) || (transports == driver.transports)));
        return !(deleted != null ? !deleted.equals(driver.deleted) : driver.deleted != null);

    }

    @Override
    public int hashCode() {
        int result = (int)id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "Address", cascade = CascadeType.ALL)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address idAddress) {
        this.address = idAddress;
    }

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(nullable = false)
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
        this.transports = transports;
    }

    public List<ScheduleEvent> getSchedule(){
        ArrayList<ScheduleEvent> schedule = new ArrayList<>();

        for(FreightTransport transport: getTransports()){
            ScheduleEvent event = new ScheduleEvent(transport.getName(), transport.getLoadDate());
            event.setEnd(transport.getUnloadDate());
            //TODO: fix messy hardcoded URL...
            event.setUrl(String.format("/transports/%d", transport.getId()));
            schedule.add(event);
        }
            return schedule;
    }

    public List<ScheduleEvent> getSchedule(Date start, Date end){

        if(start.compareTo(end) > 0){
            throw new IllegalArgumentException(String.format("Attempted to get driver's schedule with malformed" +
                    " constraint. Start date: %s, End date: %s", start, end));
        }

        ArrayList<ScheduleEvent> schedule = new ArrayList<>();

        for(FreightTransport transport: getTransports()){
            if (!(transport.getLoadDate().compareTo(start) >= 0 && transport.getUnloadDate().compareTo(end) <= 0))
                continue;
            ScheduleEvent event = new ScheduleEvent(transport.getName(), transport.getLoadDate());
            event.setEnd(transport.getUnloadDate());
            //TODO: fix messy hardcoded URL...
            event.setUrl(String.format("/transports/%d", transport.getId()));
            schedule.add(event);
        }
        return schedule;
    }

    public void addTransport(FreightTransport transport){
        transports.add(transport);
    }
}
