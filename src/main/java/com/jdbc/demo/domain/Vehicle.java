package com.jdbc.demo.domain;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Vehicle {

    private int id;
    private int horsepower;
    private int engine;
    private int mileage;
    private String type;
    private String brand;
    private String VIN;
    private Date productionDate;
    private Boolean available;
    private ArrayList<FreightTransport> transports;

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
        if (!type.equals(vehicle.type)) return false;
        if (!brand.equals(vehicle.brand)) return false;
        if (!VIN.equals(vehicle.VIN)) return false;
        if (available != null ? !available.equals(vehicle.available) : vehicle.available != null) return false;
        return ((transports == null || vehicle.transports == null) || (transports.size() == vehicle.transports.size() && vehicle.transports.containsAll(transports)));

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + horsepower;
        result = 31 * result + engine;
        result = 31 * result + mileage;
        result = 31 * result + type.hashCode();
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
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", VIN='" + VIN + '\'' +
                ", productionDate=" + productionDate +
                ", available=" + available +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

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
        this.transports = (ArrayList<FreightTransport>)transports;
    }
}
