package apitests;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbc.demo.Application;
import com.jdbc.demo.VehicleDAO;
import com.jdbc.demo.domain.psql.Vehicle;

import utils.TestModelsFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class VehicleTest {

	private MockMvc mockMvc;

	@Autowired
	private VehicleDAO vehicleManager;

	private ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
	

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void setup() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		Vehicle vehicle1 = vehicleManager.add(TestModelsFactory.createTestVehicle1());
		vehicleList.add(vehicle1);
		Vehicle vehicle2 = vehicleManager.add(TestModelsFactory.createTestVehicle2());
		vehicleList.add(vehicle2);

	}

	@After
	public void cleanup() throws Exception {

		for (Vehicle vehicle : vehicleList) {
			vehicleManager.delete(vehicle);
		}
	}

	@Test
	public void getVehicle() throws Exception {

		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Vehicle testVehicle = vehicleList.get(1);
		
		mockMvc.perform(get("/api/vehicles/" + testVehicle.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is( (int) testVehicle.getId() )))
				.andExpect(jsonPath("$.horsepower", is(testVehicle.getHorsepower())))
				.andExpect(jsonPath("$.engine", is(testVehicle.getEngine())))
				.andExpect(jsonPath("$.mileage", is(testVehicle.getMileage())))
				.andExpect(jsonPath("$.model", is(testVehicle.getModel())))
				.andExpect(jsonPath("$.brand", is(testVehicle.getBrand())))
				.andExpect(jsonPath("$.vin", is(testVehicle.getVIN())))
				.andExpect(jsonPath("$.productionDate", is(String.valueOf(format.format(testVehicle.getProductionDate())))))
				.andExpect(jsonPath("$.available", is(testVehicle.getAvailable())));
	}

	@Test
	public void postVehicle() throws Exception {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Vehicle testVehicle = TestModelsFactory.createTestVehicle3();
		testVehicle.setId(vehicleList.get(1).getId() + 1);
		vehicleList.add(testVehicle);
				

		mockMvc.perform(post("/api/vehicles")
				.contentType(contentType).content(convertObjectToJsonBytes(testVehicle)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is((int) testVehicle.getId())))
				.andExpect(jsonPath("$.horsepower", is(testVehicle.getHorsepower())))
				.andExpect(jsonPath("$.engine", is(testVehicle.getEngine())))
				.andExpect(jsonPath("$.mileage", is(testVehicle.getMileage())))
				.andExpect(jsonPath("$.model", is(testVehicle.getModel())))
				.andExpect(jsonPath("$.brand", is(testVehicle.getBrand())))
				.andExpect(jsonPath("$.vin", is(testVehicle.getVIN())))
				.andExpect(jsonPath("$.available", is(testVehicle.getAvailable())));
		
		 Assert.assertEquals(String.valueOf(vehicleManager.get(testVehicle.getId()).getProductionDate()), String.valueOf(format.format(testVehicle.getProductionDate())));
	}

	@Test
	public void deleteVehicle() throws Exception {

		Vehicle testVehicle = vehicleManager.add(TestModelsFactory.createTestVehicle3());
		vehicleList.add(testVehicle);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/vehicles/" + testVehicle.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		vehicleList.remove(testVehicle);
		Assert.assertFalse(vehicleManager.getAll().contains(testVehicle));
	}
	
	@Test
	public void getVehicles() throws Exception {

		Vehicle testVehicle1 = vehicleManager.getAll().get(0);
		Vehicle testVehicle2 = vehicleManager.getAll().get(1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(vehicleManager.getAll().size())))
				.andExpect(jsonPath("$.[0].id", is((int) testVehicle1.getId())))
				.andExpect(jsonPath("$.[0].horsepower", is(testVehicle1.getHorsepower())))
				.andExpect(jsonPath("$.[0].engine", is(testVehicle1.getEngine())))
				.andExpect(jsonPath("$.[0].mileage", is(testVehicle1.getMileage())))
				.andExpect(jsonPath("$.[0].model", is(testVehicle1.getModel())))
				.andExpect(jsonPath("$.[0].brand", is(testVehicle1.getBrand())))
				.andExpect(jsonPath("$.[0].vin", is(testVehicle1.getVIN())))
				.andExpect(jsonPath("$.[0].productionDate", is(String.valueOf(testVehicle1.getProductionDate()))))
				.andExpect(jsonPath("$.[0].available", is(testVehicle1.getAvailable())))
				.andExpect(jsonPath("$.[1].id", is((int) testVehicle2.getId())))
				.andExpect(jsonPath("$.[1].horsepower", is(testVehicle2.getHorsepower())))
				.andExpect(jsonPath("$.[1].engine", is(testVehicle2.getEngine())))
				.andExpect(jsonPath("$.[1].mileage", is(testVehicle2.getMileage())))
				.andExpect(jsonPath("$.[1].model", is(testVehicle2.getModel())))
				.andExpect(jsonPath("$.[1].brand", is(testVehicle2.getBrand())))
				.andExpect(jsonPath("$.[1].vin", is(testVehicle2.getVIN())))
				.andExpect(jsonPath("$.[1].productionDate", is(String.valueOf(testVehicle2.getProductionDate()))))
				.andExpect(jsonPath("$.[1].available", is(testVehicle2.getAvailable())));;		
	}
	
	@Test
	public void updateVehicle() throws Exception {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Vehicle testVehicle= vehicleManager.add(TestModelsFactory.createTestVehicle3());
		vehicleList.add(testVehicle);
		
		mockMvc.perform(post("/api/vehicles/" + testVehicle.getId()).contentType(contentType)
				.content(convertObjectToJsonBytes(testVehicle))
				).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is( (int) testVehicle.getId() )))
				.andExpect(jsonPath("$.horsepower", is(testVehicle.getHorsepower())))
				.andExpect(jsonPath("$.engine", is(testVehicle.getEngine())))
				.andExpect(jsonPath("$.mileage", is(testVehicle.getMileage())))
				.andExpect(jsonPath("$.model", is(testVehicle.getModel())))
				.andExpect(jsonPath("$.brand", is(testVehicle.getBrand())))
				.andExpect(jsonPath("$.vin", is(testVehicle.getVIN())))
				.andExpect(jsonPath("$.available", is(testVehicle.getAvailable())));
		
		 Assert.assertEquals(String.valueOf(vehicleManager.get(testVehicle.getId()).getProductionDate()), String.valueOf(format.format(testVehicle.getProductionDate())));
	}
	
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
