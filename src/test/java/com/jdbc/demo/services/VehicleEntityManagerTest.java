package com.jdbc.demo.services;

import com.jdbc.demo.VehicleDAO;
import com.jdbc.demo.domain.psql.Vehicle;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mciesielski on 2015-10-23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Transactional(transactionManager = "txManager")
public class VehicleEntityManagerTest {

    @Autowired
    private VehicleDAO vehicleManager;

    private ArrayList<Vehicle> testVehicles = new ArrayList<Vehicle>();

    @Before
    public void setUp() throws Exception {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setBrand("Scania");
        vehicle1.setEngine(16);
        vehicle1.setHorsepower(300);
        vehicle1.setModel("ZX-83");
        vehicle1.setVIN("1M8GDM9A_KP042777");
        vehicle1.setProductionDate(new Date(System.currentTimeMillis()));
        testVehicles.add(vehicle1);

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBrand("Scania");
        vehicle2.setEngine(16);
        vehicle2.setHorsepower(300);
        vehicle2.setModel("ZX-83");
        vehicle2.setVIN("1M8GDM9A_KE042777");
        vehicle2.setProductionDate(new Date(System.currentTimeMillis()));
        testVehicles.add(vehicle2);
    }

    @After
    public void tearDown() throws Exception {
        for (Vehicle testVehicle: testVehicles){
            vehicleManager.delete(testVehicle);
        }

    }

    @Test
    public void getAllTest() throws Exception {
        Vehicle vehicle1 = vehicleManager.add(testVehicles.get(0));
        Vehicle vehicle2 = vehicleManager.add(testVehicles.get(1));

        Assert.assertTrue(vehicleManager.getAll().contains(vehicle1));
        Assert.assertTrue(vehicleManager.getAll().contains(vehicle2));
    }

    @Test
    public void deleteTest() throws Exception {
        Vehicle vehicle1 = vehicleManager.add(testVehicles.get(0));
        Vehicle vehicle2 = vehicleManager.add(testVehicles.get(1));

        Assert.assertTrue(vehicleManager.getAll().contains(vehicle1));

        vehicleManager.delete(testVehicles.get(0));

        Assert.assertFalse(vehicleManager.getAll().contains(vehicle1));
        Assert.assertTrue(vehicleManager.getAll().contains(vehicle2));
    }

    @Test
    public void addTest() throws Exception {
        int sizeBeforeAddition = vehicleManager.getAll().size();
        Vehicle vehicle = vehicleManager.add(testVehicles.get(0));
        Assert.assertTrue(vehicleManager.getAll().contains(vehicle));
        Assert.assertEquals(sizeBeforeAddition+1, vehicleManager.getAll().size());
    }

    @Test
    public void getByIdTest() throws Exception {

        Vehicle vehicle = vehicleManager.add(testVehicles.get(0));
        List<Vehicle> all = vehicleManager.getAll();
        Assert.assertTrue(vehicleManager.getAll().contains(vehicle));

        Vehicle foundVehicle = vehicleManager.get((int)vehicle.getId());

        Assert.assertEquals(vehicle, foundVehicle);
    }

    @Test
    public void testUpdate() throws Exception {
        Vehicle vehicle1 = vehicleManager.add(testVehicles.get(0));

        Assert.assertTrue(vehicleManager.getAll().contains(vehicle1));

        vehicle1.setBrand("QWERTY");
        vehicleManager.update(vehicle1);
        Vehicle updatedVehicle1 = vehicleManager.get(vehicle1.getId());

        Assert.assertEquals(vehicle1, updatedVehicle1);

    }
}