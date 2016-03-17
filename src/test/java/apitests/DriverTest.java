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

	Driver driver1;
	Driver driver2;
	
	Driver testDriver1;
	Driver testDriver2;
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void setup() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.addressList.add(addressManager.add(TestModelsFactory.createTestAddress1()));
		driver1 = driverManager.add(TestModelsFactory.createTestDriver1(addressList.get(0)));
		driverList.add(driver1);
		driver2 = driverManager.add(TestModelsFactory.createTestDriver2(addressList.get(0)));
		driverList.add(driver2);

	}

	@After
	public void cleanup() throws Exception {

		for (Driver driver : driverList) {
			driverManager.delete(driver);
		}
	}

	@Test
	public void getDriver() throws Exception {
		
		testDriver1 = driverList.get(1);
				
		mockMvc.perform(get("/api/drivers/" + testDriver1.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is( (int) testDriver1.getId() )))
				.andExpect(jsonPath("$.firstName", is(testDriver1.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(testDriver1.getLastName())))
				.andExpect(jsonPath("$.salary", is(testDriver1.getSalary().doubleValue())))
				.andExpect(jsonPath("$.pesel", is(testDriver1.getPESEL())))
				.andExpect(jsonPath("$.available", is(testDriver1.isAvailable())))
				.andExpect(jsonPath("$.deleted", is(testDriver1.isDeleted())));
	}

	@Test
	public void postDriver() throws Exception {
		
		Driver json = TestModelsFactory.createTestDriver3(addressList.get(0));
		
		mockMvc.perform(post("/api/drivers/")
				.contentType(contentType).content(convertObjectToJsonBytes(json)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is((int) driver2.getId() + 1)))
				.andExpect(jsonPath("$.firstName", is("Jan")))
				.andExpect(jsonPath("$.lastName", is("Czapla")))
				.andExpect(jsonPath("$.salary", is(3200.00)))
				.andExpect(jsonPath("$.pesel", is("12345687911")))
				.andExpect(jsonPath("$.available", is(true)))
				.andExpect(jsonPath("$.deleted", is(false)));

		driverManager.delete(driver2.getId() + 1);
	}

	@Test
	public void deleteDriver() throws Exception {

		testDriver1 = driverManager.add(TestModelsFactory.createTestDriver3(addressList.get(0)));

		mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/drivers/" + testDriver1.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		Assert.assertFalse(driverManager.getAll().contains(testDriver1));

	}

	@Test
	public void getDrivers() throws Exception {

		testDriver1 = driverManager.getAll().get(0);
		testDriver2 = driverManager.getAll().get(1);
		
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
		
		testDriver1 = driverManager.add(TestModelsFactory.createTestDriver3(addressList.get(0)));
		driverList.add(testDriver1);
		Driver json = testDriver1;
		
		mockMvc.perform(post("/api/drivers/" + testDriver1.getId()).contentType(contentType)
				.content(convertObjectToJsonBytes(json))
				).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is((int)testDriver1.getId())))
				.andExpect(jsonPath("$.firstName", is(testDriver1.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(testDriver1.getLastName())))
				.andExpect(jsonPath("$.salary", is(testDriver1.getSalary().doubleValue())))
				.andExpect(jsonPath("$.pesel", is(testDriver1.getPESEL())))
				.andExpect(jsonPath("$.available", is(testDriver1.isAvailable())))
				.andExpect(jsonPath("$.deleted", is(testDriver1.isDeleted())));

	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
