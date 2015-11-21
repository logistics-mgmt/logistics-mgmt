package com.jdbc.demo.domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

/**
 * Created by Mateusz on 22-Oct-15.
 */
public class FreightTransport {

    private int id;
    private Address loadAddress;
    private Address unloadAddress;
    private int distance;
    private BigDecimal value;
    private Date loadDate;
    private Date unloadDate;
    private Date paymentDate;
    private Boolean finished;
    private String notes;
    private Client client;
    private List<Vehicle> vehicles;
    private List<Driver> drivers;

    public FreightTransport(){

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FreightTransport that = (FreightTransport) o;

        if (id != that.id) return false;
        if (distance != that.distance) return false;
        if (!loadAddress.equals(that.loadAddress)) return false;
        if (!unloadAddress.equals(that.unloadAddress)) return false;
        if (!value.equals(that.value)) return false;
        if (loadDate != null ? !loadDate.equals(that.loadDate) : that.loadDate != null) return false;
        if (unloadDate != null ? !unloadDate.equals(that.unloadDate) : that.unloadDate != null) return false;
        if (paymentDate != null ? !paymentDate.equals(that.paymentDate) : that.paymentDate != null) return false;
        if (finished != null ? !finished.equals(that.finished) : that.finished != null) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;
        if (!client.equals(that.client)) return false;
        if (!(vehicles.size() == that.vehicles.size() && that.vehicles.containsAll(vehicles))) return false;
        return (drivers.size() == that.drivers.size() && that.drivers.containsAll(drivers));

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + loadAddress.hashCode();
        result = 31 * result + unloadAddress.hashCode();
        result = 31 * result + distance;
        result = 31 * result + value.hashCode();
        result = 31 * result + (loadDate != null ? loadDate.hashCode() : 0);
        result = 31 * result + (unloadDate != null ? unloadDate.hashCode() : 0);
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        result = 31 * result + (finished != null ? finished.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + client.hashCode();
        result = 31 * result + (vehicles != null ? vehicles.hashCode() : 0);
        result = 31 * result + (drivers != null ? drivers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FreightTransport{" +
                "id=" + id +
                ", loadAddress=" + loadAddress +
                ", unloadAddress=" + unloadAddress +
                ", distance=" + distance +
                ", value=" + value +
                ", loadDate=" + loadDate +
                ", unloadDate=" + unloadDate +
                ", paymentDate=" + paymentDate +
                ", finished=" + finished +
                ", notes='" + notes + '\'' +
                ", client=" + client +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getLoadAddress() {
        return loadAddress;
    }

    public void setLoadAddress(Address loadAddress) {
        this.loadAddress = loadAddress;
    }

    public Address getUnloadAddress() {
        return unloadAddress;
    }

    public void setUnloadAddress(Address unloadAddress) {
        this.unloadAddress = unloadAddress;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
    }

    public Date getUnloadDate() {
        return unloadDate;
    }

    public void setUnloadDate(Date unloadDate) {
        this.unloadDate = unloadDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
