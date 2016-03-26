package com.jdbc.demo.services.maps.model;

import com.google.maps.model.DirectionsStep;
import com.google.maps.model.Distance;
import com.google.maps.model.Duration;
import com.google.maps.model.LatLng;

/**
 * Created by Mateusz on 23-Feb-16.
 */
public class MappedRouteStep {
    /**
     * Provides lightweight representation of DirectionsStep.
     */

    private Duration duration;
    private Distance distance;
    private LatLng startLocation;
    private LatLng endLocation;

    public MappedRouteStep(DirectionsStep directionsStep){
        this.distance = directionsStep.distance;
        this.duration = directionsStep.duration;
        this.endLocation = directionsStep.endLocation;
        this.startLocation = directionsStep.startLocation;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }
}
