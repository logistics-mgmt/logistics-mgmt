package com.jdbc.demo.services.planning.planners;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.VehicleDAO;
import com.jdbc.demo.domain.psql.Address;
import com.jdbc.demo.domain.psql.Client;
import com.jdbc.demo.domain.psql.Driver;
import com.jdbc.demo.domain.psql.FreightTransport;
import com.jdbc.demo.domain.psql.Vehicle;
import com.jdbc.demo.services.planning.analyzers.BasicDriverAnalyzer;
import com.jdbc.demo.services.planning.analyzers.BasicVehicleAnalyzer;

import utils.DateConverter;
import utils.TestModelsFactory;


/**
 * Created by owen on 25/03/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
public class BasicPlannerTest {

	@InjectMocks
	BasicPlanner basicPlanner;

	@Mock
	DriverDAO driverManager;

	@Mock
	VehicleDAO vehicleManager;

	private List<Address> testAddresses = new ArrayList<>();
	private List<Driver> testDrivers = new ArrayList<>();
	private List<Vehicle> testVehicles = new ArrayList<>();
	private List<Client> testClients = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		testAddresses.add(TestModelsFactory.createTestAddress1());
		testAddresses.add(TestModelsFactory.createTestAddress2());

		testDrivers.add(TestModelsFactory.createTestDriver1(testAddresses.get(0)));
		testDrivers.add(TestModelsFactory.createTestDriver2(testAddresses.get(1)));

		testClients.add(TestModelsFactory.createTestClient1(testAddresses.get(0)));

		basicPlanner = new BasicPlanner();
		basicPlanner.driverAnalyzer = new BasicDriverAnalyzer();
		basicPlanner.vehicleAnalyzer = new BasicVehicleAnalyzer();

		MockitoAnnotations.initMocks(this);
		Mockito.when(driverManager.getAll()).thenReturn(testDrivers);
		Mockito.when(vehicleManager.getAll()).thenReturn(testVehicles);



	}

	@After
	public void tearDown() throws Exception {
		testDrivers.clear();
		testVehicles.clear();
		testAddresses.clear();
		testClients.clear();
	}

	@Test
	public void testPlanTransport() {
		Vehicle testVehicle1 = TestModelsFactory.createTestVehicle1();
		testVehicle1.setMaxPayload(1000);
		testVehicles.add(testVehicle1);

		Vehicle testVehicle2 = TestModelsFactory.createTestVehicle2();
		testVehicle2.setMaxPayload(1500);
		testVehicles.add(testVehicle2);

		FreightTransport transportPlan = TestModelsFactory.createTransportPlan(testClients.get(0),
				testAddresses.get(0), testAddresses.get(1), DateConverter.createDate("2016-03-26"),
				DateConverter.createDate("2016-03-29"));

		transportPlan.setPayloadWeight(1000);

		FreightTransport plannedTransport = basicPlanner.planTransport(transportPlan);

		Assert.assertTrue(plannedTransport.getVehicles().contains(testVehicle1));
		Assert.assertFalse(plannedTransport.getVehicles().contains(testVehicle2));
		Assert.assertEquals(1, plannedTransport.getVehicles().size());
		Assert.assertEquals(1, plannedTransport.getDrivers().size());
	}

	@Test
	public void testPlanTransportAllVehicles() {
		Vehicle testVehicle1 = TestModelsFactory.createTestVehicle1();
		testVehicle1.setMaxPayload(1000);
		testVehicles.add(testVehicle1);

		Vehicle testVehicle2 = TestModelsFactory.createTestVehicle2();
		testVehicle2.setMaxPayload(1500);
		testVehicles.add(testVehicle2);

		FreightTransport transportPlan = TestModelsFactory.createTransportPlan(testClients.get(0),
				testAddresses.get(0), testAddresses.get(1), DateConverter.createDate("2016-03-26"),
				DateConverter.createDate("2016-03-29"));

		transportPlan.setPayloadWeight(2500);

		FreightTransport plannedTransport = basicPlanner.planTransport(transportPlan);

		Assert.assertTrue(plannedTransport.getVehicles().contains(testVehicle1));
		Assert.assertTrue(plannedTransport.getVehicles().contains(testVehicle2));
		Assert.assertEquals(2, plannedTransport.getVehicles().size());
		Assert.assertEquals(2, plannedTransport.getDrivers().size());
	}

	@Test
	public void testPlanTransportHalfVehicles() {
		Vehicle testVehicle1 = TestModelsFactory.createTestVehicle1();
		testVehicle1.setMaxPayload(500);
		testVehicles.add(testVehicle1);

		Vehicle testVehicle2 = TestModelsFactory.createTestVehicle2();
		testVehicle2.setMaxPayload(1000);
		testVehicles.add(testVehicle2);

		Vehicle testVehicle3 = TestModelsFactory.createTestVehicle2();
		testVehicle3.setMaxPayload(1500);
		testVehicles.add(testVehicle3);

		Vehicle testVehicle4 = TestModelsFactory.createTestVehicle2();
		testVehicle4.setMaxPayload(2000);
		testVehicles.add(testVehicle4);

		FreightTransport transportPlan = TestModelsFactory.createTransportPlan(testClients.get(0),
				testAddresses.get(0), testAddresses.get(1), DateConverter.createDate("2016-03-26"),
				DateConverter.createDate("2016-03-29"));

		transportPlan.setPayloadWeight(2100);

		FreightTransport plannedTransport = basicPlanner.planTransport(transportPlan);

		Assert.assertTrue(plannedTransport.getVehicles().contains(testVehicle1));
		Assert.assertTrue(plannedTransport.getVehicles().contains(testVehicle4));
		Assert.assertFalse(plannedTransport.getVehicles().contains(testVehicle3));
		Assert.assertFalse(plannedTransport.getVehicles().contains(testVehicle2));
		Assert.assertEquals(2, plannedTransport.getVehicles().size());
		Assert.assertEquals(2, plannedTransport.getDrivers().size());
	}

	@Test
	public void testPlanTransportVehiclesMinimalCount() {
		Vehicle testVehicle1 = TestModelsFactory.createTestVehicle1();
		testVehicle1.setMaxPayload(15000);
		testVehicles.add(testVehicle1);

		Vehicle testVehicle2 = TestModelsFactory.createTestVehicle2();
		testVehicle2.setMaxPayload(15000);
		testVehicles.add(testVehicle2);

		Vehicle testVehicle3 = TestModelsFactory.createTestVehicle2();
		testVehicle3.setMaxPayload(20000);
		testVehicles.add(testVehicle3);

		Vehicle testVehicle4 = TestModelsFactory.createTestVehicle2();
		testVehicle4.setMaxPayload(5000);
		testVehicles.add(testVehicle4);

		FreightTransport transportPlan = TestModelsFactory.createTransportPlan(testClients.get(0),
				testAddresses.get(0), testAddresses.get(1), DateConverter.createDate("2016-03-26"),
				DateConverter.createDate("2016-03-29"));

		transportPlan.setPayloadWeight(15200);

		FreightTransport plannedTransport = basicPlanner.planTransport(transportPlan);

		Assert.assertFalse(plannedTransport.getVehicles().contains(testVehicle1));
		Assert.assertFalse(plannedTransport.getVehicles().contains(testVehicle2));
		Assert.assertTrue(plannedTransport.getVehicles().contains(testVehicle3));
		Assert.assertFalse(plannedTransport.getVehicles().contains(testVehicle4));
		Assert.assertEquals(1, plannedTransport.getVehicles().size());
		Assert.assertEquals(1, plannedTransport.getDrivers().size());
	}

	public void testPlanTransportExceedingAvailablePayload() {
		Vehicle testVehicle1 = TestModelsFactory.createTestVehicle1();
		testVehicle1.setMaxPayload(10000);
		testVehicles.add(testVehicle1);

		Vehicle testVehicle2 = TestModelsFactory.createTestVehicle2();
		testVehicle2.setMaxPayload(15000);
		testVehicles.add(testVehicle2);

		FreightTransport transportPlan = TestModelsFactory.createTransportPlan(testClients.get(0),
				testAddresses.get(0), testAddresses.get(1), DateConverter.createDate("2016-03-26"),
				DateConverter.createDate("2016-03-29"));

		transportPlan.setPayloadWeight(40000);

		FreightTransport plannedTransport = basicPlanner.planTransport(transportPlan);
		Assert.assertNull(plannedTransport);
	}

}