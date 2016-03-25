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
import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.Application;
import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.domain.psql.Address;
import com.jdbc.demo.domain.psql.Driver;
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
public class DriverTest {

	private MockMvc mockMvc;

	@Autowired
	private DriverDAO driverManager;

	@Autowired
	private AddressDAO addressManager;

	private ArrayList<Driver> driverList = new ArrayList<Driver>();
	private ArrayList<Address> addressList = new ArrayList<Address>();

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void setup() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		addressList.add(addressManager.add(TestModelsFactory.createTestAddress1()));
		Driver driver1 = driverManager.add(TestModelsFactory.createTestDriver1(addressList.get(0)));
		driverList.add(driver1);
		Driver driver2 = driverManager.add(TestModelsFactory.createTestDriver2(addressList.get(0)));
		driverList.add(driver2);

	}

	@After
	public void cleanup() throws Exception {

		for (Driver driver : driverList) {
			driverManager.delete(driver.getPESEL());
		}
		
		addressManager.delete(addressList.get(0));
	}

	@Test
	public void getDriver() throws Exception {
		
		Driver testDriver = driverList.get(1);
				
		mockMvc.perform(get("/api/drivers/" + testDriver.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is( (int) testDriver.getId() )))
				.andExpect(jsonPath("$.firstName", is(testDriver.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(testDriver.getLastName())))
				.andExpect(jsonPath("$.salary", is(testDriver.getSalary().doubleValue())))
				.andExpect(jsonPath("$.pesel", is(testDriver.getPESEL())))
				.andExpect(jsonPath("$.available", is(testDriver.isAvailable())))
				.andExpect(jsonPath("$.deleted", is(testDriver.isDeleted())));
	}

	@Test
	public void postDriver() throws Exception {
		
		Driver testDriver = TestModelsFactory.createTestDriver3(addressList.get(0));
		driverList.add(testDriver);
		
		mockMvc.perform(post("/api/drivers/")
				.contentType(contentType).content(convertObjectToJsonBytes(testDriver)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is((int) driverList.get(1).getId() + 1)))
				.andExpect(jsonPath("$.firstName", is(testDriver.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(testDriver.getLastName())))
				.andExpect(jsonPath("$.salary", is(testDriver.getSalary().doubleValue())))
				.andExpect(jsonPath("$.pesel", is(testDriver.getPESEL())))
				.andExpect(jsonPath("$.available", is(testDriver.isAvailable())))
				.andExpect(jsonPath("$.deleted", is(testDriver.isDeleted())));
	}

	@Test
	public void deleteDriver() throws Exception {

		Driver testDriver = driverManager.add(TestModelsFactory.createTestDriver3(addressList.get(0)));
		driverList.add(testDriver);
		
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/drivers/" + testDriver.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		Assert.assertFalse(driverManager.getAll().contains(testDriver));
	}

	@Test
	public void getDrivers() throws Exception {

		Driver testDriver1 = driverManager.getAll().get(0);
		Driver testDriver2 = driverManager.getAll().get(1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/drivers").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(driverManager.getAll().size())))
				.andExpect(jsonPath("$.[0].id", is((int) testDriver1.getId())))
				.andExpect(jsonPath("$.[0].firstName", is(testDriver1.getFirstName())))
				.andExpect(jsonPath("$.[0].lastName", is(testDriver1.getLastName())))
				.andExpect(jsonPath("$.[0].salary", is(testDriver1.getSalary().doubleValue())))
				.andExpect(jsonPath("$.[0].pesel", is(testDriver1.getPESEL())))
				.andExpect(jsonPath("$.[0].available", is(testDriver1.isAvailable())))
				.andExpect(jsonPath("$.[0].deleted", is(testDriver1.isDeleted())))
				.andExpect(jsonPath("$.[1].id", is((int) testDriver2.getId())))
				.andExpect(jsonPath("$.[1].firstName", is(testDriver2.getFirstName())))
				.andExpect(jsonPath("$.[1].lastName", is(testDriver2.getLastName())))
				.andExpect(jsonPath("$.[1].salary", is(testDriver2.getSalary().doubleValue())))
				.andExpect(jsonPath("$.[1].pesel", is(testDriver2.getPESEL())))
				.andExpect(jsonPath("$.[1].available", is(testDriver2.isAvailable())))
				.andExpect(jsonPath("$.[1].deleted", is(testDriver2.isDeleted())));
	}

	@Test
	public void updateDriver() throws Exception {
		
		Driver testDriver= driverManager.add(TestModelsFactory.createTestDriver3(addressList.get(0)));
		driverList.add(testDriver);
		
		mockMvc.perform(post("/api/drivers/" + testDriver.getId()).contentType(contentType)
				.content(convertObjectToJsonBytes(testDriver))
				).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is((int)testDriver.getId())))
				.andExpect(jsonPath("$.firstName", is(testDriver.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(testDriver.getLastName())))
				.andExpect(jsonPath("$.salary", is(testDriver.getSalary().doubleValue())))
				.andExpect(jsonPath("$.pesel", is(testDriver.getPESEL())))
				.andExpect(jsonPath("$.available", is(testDriver.isAvailable())))
				.andExpect(jsonPath("$.deleted", is(testDriver.isDeleted())));

	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
