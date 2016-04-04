package com.jdbc.demo.services.psql;

import com.jdbc.demo.*;
import com.jdbc.demo.domain.psql.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import utils.TestModelsFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 03-Nov-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Transactional
public class FreightTransportManagerTest {

    @Autowired
    private DriverDAO driverManager;

    @Autowired
    private AddressDAO addressManager;

    @Autowired
    private ClientDAO clientManager;

    @Autowired
    private VehicleDAO vehicleManager;

    @Autowired
    private FreightTransportDAO freightTransportManager;

    private ArrayList<FreightTransport> testFreightTransports = new ArrayList<FreightTransport>();

    private List<Address> testAddresses = new ArrayList<Address>();
    private List<Client> testClients = new ArrayList<Client>();
    private List<Vehicle> testVehicles = new ArrayList<>();
    private List<Driver> testDrivers = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        testAddresses.add(addressManager.add(TestModelsFactory.createTestAddress1()));
        testAddresses.add(addressManager.add(TestModelsFactory.createTestAddress2()));

        testDrivers.add(driverManager.add(TestModelsFactory.createTestDriver1(testAddresses.get(0))));
        testDrivers.add(driverManager.add(TestModelsFactory.createTestDriver2(testAddresses.get(1))));

        testVehicles.add(vehicleManager.add(TestModelsFactory.createTestVehicle1()));
        testVehicles.add(vehicleManager.add(TestModelsFactory.createTestVehicle2()));

        testClients.add(clientManager.add(TestModelsFactory.createTestClient1(testAddresses.get(0))));
        testClients.add(clientManager.add(TestModelsFactory.createTestClient2(testAddresses.get(0))));

        testFreightTransports.add(TestModelsFactory.createTestFreightTransport1(testClients.get(0), testDrivers, testVehicles,
                testAddresses.get(0), testAddresses.get(1)));

    }

    @Test
    public void testGetAll() throws Exception {
        testFreightTransports.add(TestModelsFactory.createTestFreightTransport1(testClients.get(1), testDrivers, testVehicles,
                testAddresses.get(1), testAddresses.get(0)));

        FreightTransport freightTransport1 = freightTransportManager.add(testFreightTransports.get(0));
        FreightTransport freightTransport2 = freightTransportManager.add(testFreightTransports.get(1));

        Assert.assertTrue(freightTransportManager.getAll().size()>=2);
        Assert.assertTrue(freightTransportManager.getAll().contains(freightTransport1));
        Assert.assertTrue(freightTransportManager.getAll().contains(freightTransport2));
    }

    @Test
    public void testAdd() throws Exception {
        int sizeBeforeAddition = freightTransportManager.getAll().size();
        FreightTransport freightTransport1 = freightTransportManager.add(testFreightTransports.get(0));

        Assert.assertTrue(freightTransportManager.getAll().contains(freightTransport1));
        Assert.assertEquals(sizeBeforeAddition+1, freightTransportManager.getAll().size());
    }

    @Test
    public void testUpdate() throws Exception {
        FreightTransport freightTransport1 = freightTransportManager.add(testFreightTransports.get(0));
        freightTransport1.setDistance(1789);
        freightTransportManager.update(freightTransport1);

        FreightTransport updatedFreightTransport1 = freightTransportManager.get(freightTransport1.getId());

        Assert.assertEquals(freightTransport1, updatedFreightTransport1);
    }

    @Test
    public void testDelete() throws Exception {
        testFreightTransports.add(TestModelsFactory.createTestFreightTransport1(testClients.get(1), null, null,
                testAddresses.get(1), testAddresses.get(0)));

        FreightTransport freightTransport1 = freightTransportManager.add(testFreightTransports.get(0));
        FreightTransport freightTransport2 = freightTransportManager.add(testFreightTransports.get(1));

        Assert.assertTrue(freightTransportManager.getAll().contains(freightTransport1));

        freightTransportManager.delete(freightTransport1.getId());

        Assert.assertFalse(freightTransportManager.getAll().contains(freightTransport1));
        Assert.assertTrue(freightTransportManager.getAll().contains(freightTransport2));
    }

    @Test
    public void testGet() throws Exception {
        FreightTransport freightTransport1 = freightTransportManager.add(testFreightTransports.get(0));
        FreightTransport foundFreightTransport1 = freightTransportManager.get(freightTransport1.getId());

        Assert.assertEquals(freightTransport1, foundFreightTransport1);
    }

    @Test
    public void testGetDrivers() throws Exception {
        FreightTransport freightTransport1 = freightTransportManager.add(testFreightTransports.get(0));

        Assert.assertEquals(testDrivers, freightTransport1.getDrivers());
    }

    @Test
    public void testGetVehicles() throws Exception {
        FreightTransport freightTransport1 = freightTransportManager.add(testFreightTransports.get(0));

        Assert.assertEquals(testVehicles, freightTransport1.getVehicles());
    }

    //Moved here from VehiclesManagerTest to reduce setUp time overhead
    @Test
    public void testGetVehiclesTransports() throws Exception {
        FreightTransport freightTransport1 = freightTransportManager.add(testFreightTransports.get(0));
        Vehicle vehicle = testVehicles.get(0);
        List<FreightTransport> vehicleTransports = new ArrayList<>();
        vehicleTransports.add(freightTransport1);
        vehicle.setTransports(vehicleTransports);
        vehicleManager.update(vehicle);
        Assert.assertEquals(1, vehicle.getTransports().size());
        Assert.assertEquals(freightTransport1, vehicle.getTransports().get(0));
    }

    //Moved here from DriversManagerTest to reduce setUp time overhead
    @Test
    public void testGetDriversTransports() throws Exception {
        FreightTransport freightTransport1 = freightTransportManager.add(testFreightTransports.get(0));
        Driver driver = testDrivers.get(0);
        List<FreightTransport> driverTransports = new ArrayList<>();
        driverTransports.add(freightTransport1);
        driver.setTransports(driverTransports);
        driverManager.update(driver);
        Assert.assertEquals(1, driver.getTransports().size());
        Assert.assertEquals(freightTransport1, driver.getTransports().get(0));
    }
}