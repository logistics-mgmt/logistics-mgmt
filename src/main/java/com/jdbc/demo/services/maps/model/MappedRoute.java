package com.jdbc.demo.services.maps.model;

import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.Distance;
import com.google.maps.model.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 23-Feb-16.
 */
public class MappedRoute {
    /**
     * Provides lightweight representation of DirectionsRoute.
     */

    private String summary;
    private Duration duration;
    private Distance distance;
    private List<MappedRouteStep> steps;

    public MappedRoute(DirectionsRoute directionsRoute){
        this.summary = directionsRoute.summary;
        this.distance = directionsRoute.legs[0].distance;
        this.duration = directionsRoute.legs[0].duration;
        this.steps = new ArrayList<>();
        for(DirectionsStep step: directionsRoute.legs[0].steps){
            steps.add(new MappedRouteStep(step));
        }
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public List<MappedRouteStep> getSteps() {
        return steps;
    }

    public void setSteps(List<MappedRouteStep> steps) {
        this.steps = steps;
    }
}
