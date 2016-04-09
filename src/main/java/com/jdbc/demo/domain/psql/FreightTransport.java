package com.jdbc.demo.domain.psql;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jdbc.demo.services.maps.DirectionsService;
import com.jdbc.demo.services.maps.model.MappedRoute;

/**
 * Created by Mateusz on 22-Oct-15.
 */

@Entity
@NamedNativeQueries({
		@NamedNativeQuery(name = "freightTransport.all", query = "Select * from FreightTransport;", resultClass = FreightTransport.class),
		@NamedNativeQuery(name = "freightTransport.allActive", query = "Select * from FreightTransport WHERE"
				+ " finished = false AND load_date < CURRENT_DATE;", resultClass = FreightTransport.class),
		@NamedNativeQuery(name = "freightTransport.allPlanned", query = "Select * from FreightTransport WHERE"
				+ " finished = false AND load_date > CURRENT_DATE;", resultClass = FreightTransport.class), })
public class FreightTransport {

    @Id
    @SequenceGenerator(sequenceName = "FREIGHT_TRANSPORT_ID_SEQ", name = "FreightTransportIdSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FreightTransportIdSequence")
    @Column(name = "id_FreightTransport")
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="id_load_Address")
    private Address loadAddress;

    @ManyToOne
    @JoinColumn(name="id_unload_Address")
    private Address unloadAddress;

    private int distance;
    private BigDecimal value;

    @Column(name="payload_weight")
    private Integer payloadWeight;

    @Column(nullable = false, name="load_date")
    @Temporal(TemporalType.DATE)
    private Date loadDate;

    @Column(nullable = false, name="unload_date")
    @Temporal(TemporalType.DATE)
    private Date unloadDate;

    @Column(nullable = false, name="payment_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    private Boolean finished = false;
    private String notes;

    @ManyToOne
    @JoinColumn(name="id_Client")
    private Client client;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="FreightTransportVehicles", joinColumns = { @JoinColumn(name="id_FreightTransport") },
            inverseJoinColumns = { @JoinColumn(name="id_Vehicle") })
    private List<Vehicle> vehicles = new ArrayList<>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="FreightTransportDrivers", joinColumns = { @JoinColumn(name="id_FreightTransport") },
            inverseJoinColumns = { @JoinColumn(name="id_Driver") })
    private List<Driver> drivers = new ArrayList<>();

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
        int result = (int)id;
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
                ", payloadWeight=" + payloadWeight +
                ", finished=" + finished +
                ", notes='" + notes + '\'' +
                ", client=" + client +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Boolean isPlanned() {
        return (loadDate.compareTo(new Date()) > 0);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public MappedRoute getRoute(){
        return DirectionsService.getRoute(this.loadAddress.getTown(), this.unloadAddress.getTown());
    }

    public Integer getPayloadWeight() {
        return payloadWeight;
    }

    public void setPayloadWeight(Integer payloadWeight) {
        this.payloadWeight = payloadWeight;
    }
}
