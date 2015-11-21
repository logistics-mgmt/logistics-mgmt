package com.jdbc.demo.services;

import com.jdbc.demo.domain.Address;
import com.jdbc.demo.domain.Driver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.TestModelsFactory;

import java.util.ArrayList;

/**
 * Created by Mateusz on 22-Oct-15.
 */


public class DriverEntityManagerTest {

    private DriverEntityManager driverEntityManager = new DriverEntityManager();
    private AddressEntityManager addressEntityManager = new AddressEntityManager();
    private ArrayList<Driver> testDrivers = new ArrayList<Driver>();
    private ArrayList<Address> testAddresses = new ArrayList<Address>();

    @Before
    public void setUp() throws Exception {

        testAddresses.add(addressEntityManager.add(TestModelsFactory.createTestAddress1()));
        testDrivers.add(TestModelsFactory.createTestDriver1(testAddresses.get(0)));
        testDrivers.add(TestModelsFactory.createTestDriver2(testAddresses.get(0)));
    }

    @After
    public void tearDown() throws Exception {
        for (Driver testDriver: testDrivers){
            driverEntityManager.delete(testDriver.getId());
        }
        for (Address testAddress: testAddresses){
            addressEntityManager.delete(testAddress.getId());
        }
    }

    @Test
    public void addTest  () throws Exception {
        int sizeBeforeAddition = driverEntityManager.getAll().size();
        Driver driver = driverEntityManager.add(testDrivers.get(0));

        Assert.assertTrue(driverEntityManager.getAll().contains(driver));
        Assert.assertEquals(sizeBeforeAddition+1, driverEntityManager.getAll().size());
    }

    @Test
    public void getAllTest  () throws Exception {
        Driver driver1 = driverEntityManager.add(testDrivers.get(0));
        Driver driver2 = driverEntityManager.add(testDrivers.get(1));

        Assert.assertTrue(driverEntityManager.getAll().contains(driver1));
        Assert.assertTrue(driverEntityManager.getAll().contains(driver2));
    }

    @Test
    public void deleteTest  () throws Exception {
        Driver driver1 = driverEntityManager.add(testDrivers.get(0));
        Driver driver2 = driverEntityManager.add(testDrivers.get(1));
        driverEntityManager.delete(driver1.getId());
        Assert.assertFalse(driverEntityManager.getAll().contains(driver1));
        Assert.assertTrue(driverEntityManager.getAll().contains(driver2));
    }

    @Test
    public void getByIdTest  () throws Exception {
        Driver driver1 = driverEntityManager.add(testDrivers.get(0));

        Driver foundDriver1 = driverEntityManager.get(driver1.getId());
        Assert.assertEquals(driver1, foundDriver1);
    }

    @Test
    public void testUpdate() throws Exception {
        Driver driver1 = driverEntityManager.add(testDrivers.get(0));

        Assert.assertTrue(driverEntityManager.getAll().contains(driver1));

        driver1.setFirstName("Stefan");
        driverEntityManager.update(driver1);

        Driver updatedDriver = driverEntityManager.get(driver1.getId());

        Assert.assertEquals(driver1, updatedDriver);

    }
}
