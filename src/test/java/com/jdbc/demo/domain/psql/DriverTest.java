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
public class DriverTest {

    private final static String LOAD_DATE_STRING = "2016-02-21";
    private final static String UNLOAD_DATE_STRING = "2016-02-29";
    private final static String TRANSPORT_NAME = "test";
    private final static String EVENT_URL = "/transports/0";

    @Test
    public void testGetSchedule() throws Exception {
        Driver driver = TestModelsFactory.createTestDriver1(TestModelsFactory.createTestAddress1());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date loadDate = format.parse(LOAD_DATE_STRING);
        Date unloadDate = format.parse(UNLOAD_DATE_STRING);

        FreightTransport transport = new FreightTransport();
        transport.setName(TRANSPORT_NAME);
        transport.setLoadDate(loadDate);
        transport.setUnloadDate(unloadDate);

        driver.addTransport(transport);

        ScheduleEvent expectedEvent = new FreightTransportEvent(transport);

        Assert.assertTrue(driver.getSchedule().contains(expectedEvent));
        Assert.assertEquals(1, driver.getSchedule().size());
    }

    @Test
    public void testGetScheduleConstrained() throws Exception {
        Driver driver = TestModelsFactory.createTestDriver1(TestModelsFactory.createTestAddress1());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date loadDate = format.parse(LOAD_DATE_STRING);
        Date unloadDate = format.parse(UNLOAD_DATE_STRING);

        FreightTransport transport = new FreightTransport();
        transport.setName(TRANSPORT_NAME);
        transport.setLoadDate(loadDate);
        transport.setUnloadDate(unloadDate);

        driver.addTransport(transport);

        ScheduleEvent expectedEvent = new FreightTransportEvent(transport);

        List<ScheduleEvent> schedule = driver.getSchedule();
        Assert.assertTrue(driver.getSchedule().contains(expectedEvent));
        Assert.assertEquals(1, driver.getSchedule(loadDate, unloadDate).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetScheduleMalformedConstraint() throws Exception {
        Driver driver = TestModelsFactory.createTestDriver1(TestModelsFactory.createTestAddress1());

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        // Mind that dates are reversed
        Date loadDate = format.parse(UNLOAD_DATE_STRING);
        Date unloadDate = format.parse(LOAD_DATE_STRING);

        FreightTransport transport = new FreightTransport();
        transport.setName(TRANSPORT_NAME);
        transport.setLoadDate(loadDate);
        transport.setUnloadDate(unloadDate);

        driver.addTransport(transport);

        driver.getSchedule(loadDate, unloadDate);
    }
}