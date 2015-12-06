package com.jdbc.demo.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties({"transports"})
@NamedNativeQueries({
        @NamedNativeQuery(name = "vehicle.all", query = "Select * from Vehicle", resultClass = Vehicle.class),
})
public class Vehicle {

    @Id
    @SequenceGenerator(sequenceName = "VEHICLE_ID_SEQ", name = "VehicleIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VehicleIdSequence")
    @Column(name = "id_Vehicle")
    private long id;

    private int horsepower;
    private int engine;
    private int mileage;
    private String model;
    private String brand;
    private String VIN;

    @Column(nullable = false, name="production_date")
    @Temporal(TemporalType.DATE)
    private Date productionDate;

    private Boolean available;

    @ManyToMany
    @JoinTable(name="FreightTransportVehicles", joinColumns = { @JoinColumn(name="id_Vehicle") },
            inverseJoinColumns = { @JoinColumn(name="id_FreightTransport") })
    private List<FreightTransport> transports = new ArrayList<>();

    public Vehicle() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (id != vehicle.id) return false;
        if (horsepower != vehicle.horsepower) return false;
        if (engine != vehicle.engine) return false;
        if (mileage != vehicle.mileage) return false;
        if (!model.equals(vehicle.model)) return false;
        if (!brand.equals(vehicle.brand)) return false;
        if (!VIN.equals(vehicle.VIN)) return false;
        if (available != null ? !available.equals(vehicle.available) : vehicle.available != null) return false;
        return ((transports == null || vehicle.transports == null) || (transports == vehicle.transports));

    }

    @Override
    public int hashCode() {
        int result = (int)id;
        result = 31 * result + horsepower;
        result = 31 * result + engine;
        result = 31 * result + mileage;
        result = 31 * result + model.hashCode();
        result = 31 * result + brand.hashCode();
        result = 31 * result + VIN.hashCode();
        result = 31 * result + productionDate.hashCode();
        result = 31 * result + (available != null ? available.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", horsepower=" + horsepower +
                ", engine=" + engine +
                ", mileage=" + mileage +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", VIN='" + VIN + '\'' +
                ", productionDate=" + productionDate +
                ", available=" + available +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    @Column(nullable = false)
    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    @Column(nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(nullable = false)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(unique = true, nullable = false)
    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Column(nullable = false)
    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public List<FreightTransport> getTransports() {
        return transports;
    }

    public void setTransports(List<FreightTransport> transports) {
        this.transports = transports;
    }
}
