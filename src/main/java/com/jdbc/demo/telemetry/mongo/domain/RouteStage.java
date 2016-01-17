package com.jdbc.demo.telemetry.mongo.domain;

import com.google.maps.model.Distance;
import com.jdbc.demo.telemetry.maps.DistanceUtils;

/**
 * Created by Mateusz on 05-Jan-16.
 */
public class RouteStage {

    private Distance distance;
    private long duration;
    private RouteWaypoint origin;
    private RouteWaypoint destination;

    public RouteStage(RouteWaypoint origin, RouteWaypoint destination){
        if (origin == null || destination == null)
            throw new IllegalArgumentException("Origin and destination must be defined for RouteStage");

        this.origin = origin;
        this.destination = destination;
        this.distance = DistanceUtils.getDistance(origin.getLocation(), destination.getLocation());
        this.duration = destination.getTimestamp().getTime() - origin.getTimestamp().getTime();
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public RouteWaypoint getOrigin() {
        return origin;
    }

    public void setOrigin(RouteWaypoint origin) {
        this.origin = origin;
    }

    public RouteWaypoint getDestination() {
        return destination;
    }

    public void setDestination(RouteWaypoint destination) {
        this.destination = destination;
    }

    public long getDuration() {
        return duration;
    }

    public String getDurationInHours(){
        return String.format("%d h", duration/3600000);
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
