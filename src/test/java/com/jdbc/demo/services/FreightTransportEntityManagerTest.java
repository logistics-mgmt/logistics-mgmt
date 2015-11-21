package com.jdbc.demo.services;

import com.jdbc.demo.domain.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.TestModelsFactory;

import java.util.ArrayList;

/**
 * Created by Mateusz on 03-Nov-15.
 */
public class FreightTransportEntityManagerTest {

    private FreightTransportEntityManager freightTransportEntityManager = new FreightTransportEntityManager();

    private AddressEntityManager addressEntityManager = new AddressEntityManager();
    private ClientEntityManager clientEntityManager = new ClientEntityManager();
    private VehicleEntityManager vehicleEntityManager = new VehicleEntityManager();
    private DriverEntityManager driverEntityManager = new DriverEntityManager();

    private ArrayList<FreightTransport> testFreightTransports = new ArrayList<FreightTransport>();

    private ArrayList<Address> testAddresses = new ArrayList<Address>();
    private ArrayList<Client> testClients = new ArrayList<Client>();
    private ArrayList<Vehicle> testVehicles = new ArrayList<Vehicle>();
    private ArrayList<Driver> testDrivers = new ArrayList<Driver>();

    @Before
    public void setUp() throws Exception {
        testAddresses.add(addressEntityManager.add(TestModelsFactory.createTestAddress1()));
        testAddresses.add(addressEntityManager.add(TestModelsFactory.createTestAddress2()));

        testDrivers.add(driverEntityManager.add(TestModelsFactory.createTestDriver1(testAddresses.get(0))));
        testDrivers.add(driverEntityManager.add(TestModelsFactory.createTestDriver2(testAddresses.get(1))));

        testVehicles.add(vehicleEntityManager.add(TestModelsFactory.createTestVehicle1()));
        testVehicles.add(vehicleEntityManager.add(TestModelsFactory.createTestVehicle2()));

        testClients.add(clientEntityManager.add(TestModelsFactory.createTestClient1(testAddresses.get(0))));
        testClients.add(clientEntityManager.add(TestModelsFactory.createTestClient2(testAddresses.get(0))));

        testFreightTransports.add(TestModelsFactory.createTestFreightTransport1(testClients.get(0), testDrivers, testVehicles,
                testAddresses.get(0), testAddresses.get(1)));

    }

    @After
    public void tearDown() throws Exception {
        for (FreightTransport freightTransport: testFreightTransports){
            freightTransportEntityManager.delete(freightTransport.getId());
        }
        for (Client testClient : testClients) {
            clientEntityManager.delete(testClient.getId());
        }
        for (Vehicle testVehicle: testVehicles){
            vehicleEntityManager.delete(testVehicle);
        }
        for (Driver testDriver: testDrivers){
            driverEntityManager.delete(testDriver.getId());
        }
        for (Address testAddress : testAddresses) {
            addressEntityManager.delete(testAddress.getId());
        }
    }

    @Test
    public void testGetAll() throws Exception {
        testFreightTransports.add(TestModelsFactory.createTestFreightTransport1(testClients.get(1), testDrivers, testVehicles,
                testAddresses.get(1), testAddresses.get(0)));

        FreightTransport freightTransport1 = freightTransportEntityManager.add(testFreightTransports.get(0));
        FreightTransport freightTransport2 = freightTransportEntityManager.add(testFreightTransports.get(1));

        Assert.assertTrue(freightTransportEntityManager.getAll().size()>=2);
        Assert.assertTrue(freightTransportEntityManager.getAll().contains(freightTransport1));
        Assert.assertTrue(freightTransportEntityManager.getAll().contains(freightTransport2));
    }

    @Test
    public void testAdd() throws Exception {
        int sizeBeforeAddition = freightTransportEntityManager.getAll().size();
        FreightTransport freightTransport1 = freightTransportEntityManager.add(testFreightTransports.get(0));

        Assert.assertTrue(freightTransportEntityManager.getAll().contains(freightTransport1));
        Assert.assertEquals(sizeBeforeAddition+1, freightTransportEntityManager.getAll().size());
    }

    @Test
    public void testUpdate() throws Exception {
        FreightTransport freightTransport1 = freightTransportEntityManager.add(testFreightTransports.get(0));
        freightTransport1.setDistance(1789);
        freightTransportEntityManager.update(freightTransport1);

        FreightTransport updatedFreightTransport1 = freightTransportEntityManager.get(freightTransport1.getId());

        Assert.assertEquals(freightTransport1, updatedFreightTransport1);
    }

    @Test
    public void testDelete() throws Exception {
        testFreightTransports.add(TestModelsFactory.createTestFreightTransport1(testClients.get(1), testDrivers, testVehicles,
                testAddresses.get(1), testAddresses.get(0)));

        FreightTransport freightTransport1 = freightTransportEntityManager.add(testFreightTransports.get(0));
        FreightTransport freightTransport2 = freightTransportEntityManager.add(testFreightTransports.get(1));

        Assert.assertTrue(freightTransportEntityManager.getAll().contains(freightTransport1));

        freightTransportEntityManager.delete(freightTransport1.getId());

        Assert.assertFalse(freightTransportEntityManager.getAll().contains(freightTransport1));
        Assert.assertTrue(freightTransportEntityManager.getAll().contains(freightTransport2));
    }

    @Test
    public void testGet() throws Exception {
        FreightTransport freightTransport1 = freightTransportEntityManager.add(testFreightTransports.get(0));
        FreightTransport foundFreightTransport1 = freightTransportEntityManager.get(freightTransport1.getId());

        Assert.assertEquals(freightTransport1, foundFreightTransport1);
    }

    @Test
    public void testGetDrivers() throws Exception {
        FreightTransport freightTransport1 = freightTransportEntityManager.add(testFreightTransports.get(0));

        Assert.assertEquals(freightTransport1.getDrivers(),
                freightTransportEntityManager.getDrivers(freightTransport1.getId()));
    }

    @Test
    public void testGetVehicles() throws Exception {
        FreightTransport freightTransport1 = freightTransportEntityManager.add(testFreightTransports.get(0));

        Assert.assertEquals(freightTransport1.getVehicles(),
                freightTransportEntityManager.getVehicles(freightTransport1.getId()));
    }

    //Moved here from VehiclesEntityManagerTest to reduce setUp time overhead
    @Test
    public void testGetVehiclesTransports() throws Exception {
        FreightTransport freightTransport1 = freightTransportEntityManager.add(testFreightTransports.get(0));
        Vehicle vehicle = testVehicles.get(0);
        vehicle.setTransports(testFreightTransports);
        Assert.assertEquals(vehicle.getTransports(),
                vehicleEntityManager.getTransports(vehicle.getId(), freightTransportEntityManager));
    }

    //Moved here from DriversEntityManagerTest to reduce setUp time overhead
    @Test
    public void testGetDriversTransports() throws Exception {
        FreightTransport freightTransport1 = freightTransportEntityManager.add(testFreightTransports.get(0));
        Driver driver = testDrivers.get(0);
        driver.setTransports(testFreightTransports);
        Assert.assertEquals(driver.getTransports(),
                driverEntityManager.getTransports(driver.getId(), freightTransportEntityManager));
    }
}