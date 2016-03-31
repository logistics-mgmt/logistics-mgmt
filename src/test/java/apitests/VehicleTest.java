package apitests;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbc.demo.Application;
import com.jdbc.demo.VehicleDAO;
import com.jdbc.demo.domain.psql.Address;
import com.jdbc.demo.domain.psql.Driver;
import com.jdbc.demo.domain.psql.Vehicle;

import utils.TestModelsFactory;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.annotation.JsonInclude;

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
		
		//Vehicle testVehicle = vehicleManager.getAll().get(0);
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
				.andExpect(jsonPath("$.productionDate", is(testVehicle.getProductionDate())))
				.andExpect(jsonPath("$.available", is(testVehicle.getAvailable())));
	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
