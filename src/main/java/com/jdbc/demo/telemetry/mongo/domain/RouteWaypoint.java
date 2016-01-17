package com.jdbc.demo.telemetry.mongo.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Mateusz on 28-Dec-15.
 */

@Document
public class RouteWaypoint {

    private long freightTransportId;
    private long driverId;
    private long vehicleId;
    private String location;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date timestamp;

    public RouteWaypoint(){

    }

    public RouteWaypoint(long freightTransportId, long driverId, long vehicleId, String location, Date timestamp) {
        this.freightTransportId = freightTransportId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.location = location;
        this.timestamp = timestamp;
    }

    public long getFreightTransportId() {
        return freightTransportId;
    }

    public void setFreightTransportId(long freightTransportId) {
        this.freightTransportId = freightTransportId;
    }

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "RouteWaypoint{" +
                "freightTransportId=" + freightTransportId +
                ", driverId=" + driverId +
                ", vehicleId=" + vehicleId +
                ", location='" + location + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
