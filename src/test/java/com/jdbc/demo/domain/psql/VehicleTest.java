package com.jdbc.demo.domain.psql;

import com.jdbc.demo.domain.schedule.FreightTransportEvent;
import com.jdbc.demo.domain.schedule.ScheduleEvent;
import org.junit.Assert;
import org.junit.Test;
import utils.TestModelsFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Mateusz on 21-Feb-16.
 */
public class VehicleTest {

    private final static String LOAD_DATE_STRING = "2016-02-21";
    private final static String UNLOAD_DATE_STRING = "2016-02-29";
    private final static String TRANSPORT_NAME = "test";

    @Test
    public void testGetSchedule() throws Exception {
        Vehicle vehicle = TestModelsFactory.createTestVehicle1();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date loadDate = format.parse(LOAD_DATE_STRING);
        Date unloadDate = format.parse(UNLOAD_DATE_STRING);

        FreightTransport transport = new FreightTransport();
        transport.setName(TRANSPORT_NAME);
        transport.setLoadDate(loadDate);
        transport.setUnloadDate(unloadDate);

        vehicle.addTransport(transport);

        ScheduleEvent expectedEvent = new FreightTransportEvent(transport);

        Assert.assertTrue(vehicle.getSchedule().contains(expectedEvent));
        Assert.assertEquals(1, vehicle.getSchedule().size());
    }

    @Test
    public void testGetScheduleConstrained() throws Exception {
        Vehicle vehicle = TestModelsFactory.createTestVehicle1();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date loadDate = format.parse(LOAD_DATE_STRING);
        Date unloadDate = format.parse(UNLOAD_DATE_STRING);

        FreightTransport transport = new FreightTransport();
        transport.setName(TRANSPORT_NAME);
        transport.setLoadDate(loadDate);
        transport.setUnloadDate(unloadDate);

        vehicle.addTransport(transport);

        ScheduleEvent expectedEvent = new FreightTransportEvent(transport);

        List<ScheduleEvent> schedule = vehicle.getSchedule();
        Assert.assertTrue(vehicle.getSchedule().contains(expectedEvent));
        Assert.assertEquals(1, vehicle.getSchedule(loadDate, unloadDate).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetScheduleMalformedConstraint() throws Exception {
        Vehicle vehicle = TestModelsFactory.createTestVehicle1();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        // Mind that dates are reversed
        Date loadDate = format.parse(UNLOAD_DATE_STRING);
        Date unloadDate = format.parse(LOAD_DATE_STRING);

        FreightTransport transport = new FreightTransport();
        transport.setName(TRANSPORT_NAME);
        transport.setLoadDate(loadDate);
        transport.setUnloadDate(unloadDate);

        vehicle.addTransport(transport);

        vehicle.getSchedule(loadDate, unloadDate);
    }
}