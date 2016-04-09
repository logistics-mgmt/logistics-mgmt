package com.jdbc.demo.services.maps;

import org.junit.Assert;
import org.junit.Test;

import com.google.maps.model.LatLng;

/**
 * Created by Mateusz on 23-Feb-16.
 */
public class GeocodingServiceTest {

    private final static String TEST_ADDRESS = "Warszawa";
    private final static String PRECISE_TEST_ADDRESS = "Marszałkowska 98, 00-514 Warszawa, Poland";
    private final static LatLng TEST_COORDINATES = new LatLng(52.2296756, 21.0122287);

    @Test
    public void testGetCoordinates() throws Exception {
        LatLng coords = GeocodingService.getCoordinates(TEST_ADDRESS);
        Assert.assertEquals(TEST_COORDINATES.lat, GeocodingService.getCoordinates(TEST_ADDRESS).lat, 0);
        Assert.assertEquals(TEST_COORDINATES.lng, GeocodingService.getCoordinates(TEST_ADDRESS).lng, 0);
    }

    @Test
    public void testGetAddress() throws Exception {
        Assert.assertEquals(PRECISE_TEST_ADDRESS, GeocodingService.getAddress(TEST_COORDINATES));
    }
}