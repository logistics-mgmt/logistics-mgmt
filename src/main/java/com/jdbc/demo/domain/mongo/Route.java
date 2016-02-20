package com.jdbc.demo.domain.mongo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 05-Jan-16.
 */
public class Route {

    private List<RouteStage> stages;

    public Route(List<RouteWaypoint> waypoints){
        List<RouteStage> stages = new ArrayList<>();
        for(int i=0; i<waypoints.size()-1; ++i){
            stages.add(new RouteStage(waypoints.get(i), waypoints.get(i+1)));
        }

        if(!validateStages(stages))
            throw new IllegalArgumentException("Stages for route are invalid.");
        this.stages=stages;
    }

    public static boolean validateStages(List<RouteStage> stages){
        for(int i =0; i<stages.size()-1; ++i){
            if(stages.get(i).getDestination() != stages.get(i+1).getOrigin())
                return false;
        }

        return true;
    }

    //TODO: implement method for static Route creation for given transportId and vehicleId
    public static Route getRoute(long transportId, long vehicleId){
        return null;
    }


    public long getDuration(){
        long duration = 0;
        for(RouteStage stage: stages){
            duration += stage.getDuration();
        }
        return duration;
    }

    public List<RouteStage> getStages() {
        return stages;
    }

    public void setStages(List<RouteStage> stages) {
        this.stages = stages;
    }

    public String getWaypointsString(){
        /**
         * Builds waypoint sequence string used in Google Maps API calls
         */

        if(stages.size()==0)
            return null;

        StringBuilder builder = new StringBuilder();

        for(int i =0; i<stages.size()-1; ++i){
            builder.append(stages.get(i).getOrigin().getLocation());
            builder.append('|');
        }

        builder.append(stages.get(stages.size()-1).getDestination().getLocation());

        if(builder.length()==0)
            return null;
        else
            return builder.toString(); // return string without the trailing pipe
    }

    public List<RouteWaypoint> getWaypointsList(){
        // Perhaps some more elegant implementation could be added in th future...
        List<RouteWaypoint> waypointsList = new ArrayList<>();

        for(int i =0; i<stages.size()-1; ++i){
            waypointsList.add(stages.get(i).getOrigin());
        }

        waypointsList.add(stages.get(stages.size()-1).getDestination());

        return waypointsList;
    }

    public String getDestinationLocation(){
        return stages.get(stages.size()-1).getDestination().getLocation();
    }

    public String getOriginLocation(){
        return stages.get(0).getOrigin().getLocation();
    }

}
