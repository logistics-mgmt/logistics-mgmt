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
import java.util.List;
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

	List<Driver> drivers;

	Driver driver1;
	Driver driver2;
	Driver driver3;

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
		drivers = driverManager.getAll();

	}

	@After
	public void cleanup() throws Exception {

		for (Driver driverList : driverList) {
			driverManager.delete(driverList);
		}
	}

	@Test
	public void getDriver() throws Exception {
		long LongID = driverList.get(1).getId();
		int IntID = (int) LongID;
		
		mockMvc.perform(get("/api/drivers/" + driverList.get(1).getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(IntID)))
				.andExpect(jsonPath("$.firstName", is(driverList.get(1).getFirstName())))
				.andExpect(jsonPath("$.lastName", is(driverList.get(1).getLastName())))
				.andExpect(jsonPath("$.salary", is(driverList.get(1).getSalary().doubleValue())))
				.andExpect(jsonPath("$.pesel", is(driverList.get(1).getPESEL())))
				.andExpect(jsonPath("$.available", is(driverList.get(1).isAvailable())))
				.andExpect(jsonPath("$.deleted", is(driverList.get(1).isDeleted())));
	}

	@Test
	public void postDriver() throws Exception {

		long LongID = driver2.getId() + 1;
		int IntID = (int) LongID;

		driver3 = TestModelsFactory.createTestDriver3(addressList.get(0));
		Driver json = driver3;
		mockMvc.perform(post("/api/drivers/")
				.contentType(contentType).content(convertObjectToJsonBytes(json)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(IntID)))
				.andExpect(jsonPath("$.firstName", is(driver3.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(driver3.getLastName())))
				.andExpect(jsonPath("$.salary", is(driver3.getSalary().doubleValue())))
				.andExpect(jsonPath("$.pesel", is(driver3.getPESEL())))
				.andExpect(jsonPath("$.available", is(driver3.isAvailable())))
				.andExpect(jsonPath("$.deleted", is(driver3.isDeleted())));

		driverManager.delete(driver2.getId() + 1);
	}

	@Test
	public void deleteDriver() throws Exception {

		driver3 = driverManager.add(TestModelsFactory.createTestDriver3(addressList.get(0)));

		mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/drivers/" + driver3.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		Assert.assertFalse(drivers.contains(driver3));

	}

	@Test
	public void getDrivers() throws Exception {
		long firstLongID = drivers.get(0).getId();
		long secendLongID = drivers.get(1).getId();
		int firstIntID = (int) firstLongID;
		int secendIntID = (int) secendLongID;
		mockMvc.perform(MockMvcRequestBuilders.get("/api/drivers").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(drivers.size())))
				.andExpect(jsonPath("$.[0].id", is(firstIntID)))
				.andExpect(jsonPath("$.[0].firstName", is(drivers.get(0).getFirstName())))
				.andExpect(jsonPath("$.[0].lastName", is(drivers.get(0).getLastName())))
				.andExpect(jsonPath("$.[0].salary", is(drivers.get(0).getSalary().doubleValue())))
				.andExpect(jsonPath("$.[0].pesel", is(drivers.get(0).getPESEL())))
				.andExpect(jsonPath("$.[0].available", is(drivers.get(0).isAvailable())))
				.andExpect(jsonPath("$.[0].deleted", is(drivers.get(0).isDeleted())))
				.andExpect(jsonPath("$.[1].id", is(secendIntID)))
				.andExpect(jsonPath("$.[1].firstName", is(drivers.get(1).getFirstName())))
				.andExpect(jsonPath("$.[1].lastName", is(drivers.get(1).getLastName())))
				.andExpect(jsonPath("$.[1].salary", is(drivers.get(1).getSalary().doubleValue())))
				.andExpect(jsonPath("$.[1].pesel", is(drivers.get(1).getPESEL())))
				.andExpect(jsonPath("$.[1].available", is(drivers.get(1).isAvailable())))
				.andExpect(jsonPath("$.[1].deleted", is(drivers.get(1).isDeleted())));
	}

	@Test
	public void updateDriver() throws Exception {
		
		driver3 = driverManager.add(TestModelsFactory.createTestDriver3(addressList.get(0)));
		driverList.add(driver3);
		Driver json = driver3;

		long LongID = driver3.getId();
		int IntID = (int) LongID;
		
		mockMvc.perform(post("/api/drivers/" + driver3.getId()).contentType(contentType)
				.content(convertObjectToJsonBytes(json))
				).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is(IntID)))
				.andExpect(jsonPath("$.firstName", is(driver3.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(driver3.getLastName())))
				.andExpect(jsonPath("$.salary", is(driver3.getSalary().doubleValue())))
				.andExpect(jsonPath("$.pesel", is(driver3.getPESEL())))
				.andExpect(jsonPath("$.available", is(driver3.isAvailable())))
				.andExpect(jsonPath("$.deleted", is(driver3.isDeleted())));

	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
