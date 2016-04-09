package com.jdbc.demo.services.maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;

/**
 * Created by Mateusz on 23-Feb-16.
 * Utility class for accessing Google Maps Geocoding API.
 */
public class GeocodingService extends MapsService {

    private static Logger LOGGER = LoggerFactory.getLogger(GeocodingService.class);

    public static LatLng getCoordinates(String address){
        /**
         * Returns coordinate(latitude and longitude) for passed address.
         * Make sure that passed address is not ambiguous - only one geocoding result is returned!
         */
        if(address == null){
            throw new IllegalArgumentException("Address must be provided.");
        }

        GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
        return getGeocodingResults(context, address)[0].geometry.location;
    }

    public static String getAddress(LatLng coordinate){
        /**
         * Returns array of addresses for passed coordinate(latitude and longitude).
         * Only first(best match) reversed geocoding result is returned!
         *
         */
        if(coordinate == null){
            throw new IllegalArgumentException("Coordinate must be provided.");
        }

        GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);
        return getReverseGeocodingResults(context, coordinate)[0].formattedAddress;
    }

    private static GeocodingResult[] getGeocodingResults(GeoApiContext context, String address){
        GeocodingResult result[] = null;
        try{
            LOGGER.info(String.format("Geocoding %s. ", address));
            result = GeocodingApi.geocode(context, address).await();
        }
        catch(Exception ex){
            LOGGER.error(String.format("Exception has been thrown during geocoding of following address: %s.",
                    address), ex);
        }
        return result;
    }

    private static GeocodingResult[] getReverseGeocodingResults(GeoApiContext context, LatLng coordinate){
        GeocodingResult result[] = null;
        try{
            LOGGER.info(String.format("Reverse geocoding %s. ", coordinate));
            result = GeocodingApi.reverseGeocode(context, coordinate).await();
        }
        catch(Exception ex){
            LOGGER.error(String.format("Exception has been thrown during reverse geocoding of following coordinate: %s.",
                    coordinate), ex);
        }
        return result;
    }
}
