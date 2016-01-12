package com.jdbc.demo.telemetry.maps;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.Distance;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mateusz on 02-Jan-16.
 */
public class DistanceUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(DistanceUtils.class);
    private final static String API_KEY = MapsConfiguration.getServerApiKey();


    public static List<Distance> getDistances(String[] origin, String[] destination){
        /**
         * Calculates distances for batch of origin and destination locations.
         */
        GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);

        DistanceMatrix matrix = getDistanceMatrix(context, origin, destination);

        List<Distance> distances = new ArrayList<>();

        for (DistanceMatrixRow row : matrix.rows){
            distances.add(row.elements[0].distance);
        }

        return distances;
    }

    public static Distance getDistance(String origin, String destination){
        /**
         * Convenience method for calculating distance between single pair of origin and destination locations.
         */
        String[] originArray = {origin};
        String[] destinationArray = {destination};
        GeoApiContext context = new GeoApiContext().setApiKey(API_KEY);

        DistanceMatrix matrix = getDistanceMatrix(context, originArray, destinationArray);

        return matrix.rows[0].elements[0].distance;
    }

    private static DistanceMatrix getDistanceMatrix(GeoApiContext context, String[] origin,
                                                    String[] destination)
    {

        DistanceMatrix matrix = null;

        try {
            LOGGER.info(String.format("Calculating distance between %s and %s. ", Arrays.toString(origin),
                    Arrays.toString(destination)));
            matrix = DistanceMatrixApi.getDistanceMatrix(context, origin, destination).await();
        }
        catch (Exception ex){
            LOGGER.error(String.format("Exception has been thrown during distance calculation between %s and %s.",
                    Arrays.toString(origin),Arrays.toString(destination)), ex);
        }

        return matrix;
    }
}
