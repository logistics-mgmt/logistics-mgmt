package com.jdbc.demo.services.maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;
import com.jdbc.demo.services.maps.model.MappedRoute;

/**
 * Created by Mateusz on 23-Feb-16.
 * Utility class for accessing Google Maps Directions API.
 */
public class DirectionsService extends MapsService {

    private static Logger LOGGER = LoggerFactory.getLogger(DirectionsService.class);

    public static MappedRoute getRoute(String origin, String destination){
        /**
         * Returns Route for given origin and destination locations.
         * Only first(most matching) found route is returned.
         */
        if(origin == null || destination == null){
            throw new IllegalArgumentException("Both origin and distance values must be provided.");
        }

        GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);

        return new MappedRoute(getRoute(context, origin, destination)[0]);

    }

    private static DirectionsRoute[] getRoute(GeoApiContext context, String origin, String destination){

        DirectionsRoute[] result = null;
        try{
            LOGGER.info(String.format("Calculating route between %s and %s. ", origin, destination));
            result = DirectionsApi.getDirections(context, origin, destination).await();
        }
        catch (Exception ex){
            LOGGER.error(String.format("Exception encountered during route calculation between %s and %s. ",
                    origin, destination), ex);
        }

        return result;
    }

}
