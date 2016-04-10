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
import com.jdbc.demo.domain.psql.Address;
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
public class AddressTest {

	private MockMvc mockMvc;

	@Autowired
	private AddressDAO addressManager;

	private ArrayList<Address> addressList = new ArrayList<Address>();

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void setup() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		Address address1 = addressManager.add(TestModelsFactory.createTestAddress1());
		addressList.add(address1);
		Address address2 = addressManager.add(TestModelsFactory.createTestAddress2());
		addressList.add(address2);

	}

	@After
	public void cleanup() throws Exception {

		for (Address address : addressList) {
			addressManager.delete(address);
		}
	}

	@Test
	public void getAddress() throws Exception {
		
		Address testAddress = addressManager.getAll().get(0);
				
		mockMvc.perform(get("/api/addresses/" + testAddress.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is((int) testAddress.getId())))
				.andExpect(jsonPath("$.town", is(testAddress.getTown())))
				.andExpect(jsonPath("$.street", is(testAddress.getStreet())))
				.andExpect(jsonPath("$.code", is(testAddress.getCode())))
				.andExpect(jsonPath("$.houseNumber", is(testAddress.getHouseNumber())))
				.andExpect(jsonPath("$.country", is(testAddress.getCountry())));	
	}
	
	@Test
	public void postAddress() throws Exception {
		
		Address testAddress = TestModelsFactory.createTestAddress3();
		testAddress.setId(addressList.get(1).getId() + 1);
		addressList.add(testAddress);
		
		mockMvc.perform(post("/api/addresses")
				.contentType(contentType).content(convertObjectToJsonBytes(testAddress)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is((int) testAddress.getId())))
				.andExpect(jsonPath("$.town", is(testAddress.getTown())))
				.andExpect(jsonPath("$.street", is(testAddress.getStreet())))
				.andExpect(jsonPath("$.code", is(testAddress.getCode())))
				.andExpect(jsonPath("$.houseNumber", is(testAddress.getHouseNumber())))
				.andExpect(jsonPath("$.country", is(testAddress.getCountry())));
	}

	@Test
	public void deleteAddress() throws Exception {

		Address testAddress = addressManager.add(TestModelsFactory.createTestAddress3());
		addressList.add(testAddress);
		
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/addresses/" + testAddress.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		addressList.remove(testAddress);
		Assert.assertFalse(addressManager.getAll().contains(testAddress));
	}

	@Test
	public void getAddresses() throws Exception {

		Address testAddress1 = addressManager.getAll().get(0);
		Address testAddress2 = addressManager.getAll().get(1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/addresses").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(addressManager.getAll().size())))
				.andExpect(jsonPath("$[0].id", is((int) testAddress1.getId())))
				.andExpect(jsonPath("$[0].town", is(testAddress1.getTown())))
				.andExpect(jsonPath("$[0].street", is(testAddress1.getStreet())))
				.andExpect(jsonPath("$[0].code", is(testAddress1.getCode())))
				.andExpect(jsonPath("$[0].houseNumber", is(testAddress1.getHouseNumber())))
				.andExpect(jsonPath("$[0].country", is(testAddress1.getCountry())))
				.andExpect(jsonPath("$[1].id", is((int) testAddress2.getId())))
				.andExpect(jsonPath("$[1].town", is(testAddress2.getTown())))
				.andExpect(jsonPath("$[1].street", is(testAddress2.getStreet())))
				.andExpect(jsonPath("$[1].code", is(testAddress2.getCode())))
				.andExpect(jsonPath("$[1].houseNumber", is(testAddress2.getHouseNumber())))
				.andExpect(jsonPath("$[1].country", is(testAddress2.getCountry())));		
	}

	@Test
	public void updateAddress() throws Exception {
		
		Address testAddress= addressManager.add(TestModelsFactory.createTestAddress3());
		addressList.add(testAddress);
		
		mockMvc.perform(post("/api/addresses/" + testAddress.getId()).contentType(contentType)
				.content(convertObjectToJsonBytes(testAddress))
				).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is((int) testAddress.getId())))
				.andExpect(jsonPath("$.town", is(testAddress.getTown())))
				.andExpect(jsonPath("$.street", is(testAddress.getStreet())))
				.andExpect(jsonPath("$.code", is(testAddress.getCode())))
				.andExpect(jsonPath("$.houseNumber", is(testAddress.getHouseNumber())))
				.andExpect(jsonPath("$.country", is(testAddress.getCountry())));
	}
	
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
