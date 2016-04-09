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
import com.jdbc.demo.ClientDAO;
import com.jdbc.demo.domain.psql.Address;
import com.jdbc.demo.domain.psql.Client;
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
public class ClientTest {

	private MockMvc mockMvc;

	@Autowired
	private ClientDAO clientManager;

	@Autowired
	private AddressDAO addressManager;

	private ArrayList<Client> clientList = new ArrayList<Client>();
	private ArrayList<Address> addressList = new ArrayList<Address>();

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void setup() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		this.addressList.add(addressManager.add(TestModelsFactory.createTestAddress1()));
		Client client1 = clientManager.add(TestModelsFactory.createTestClient1(addressList.get(0)));
		clientList.add(client1);
		Client client2 = clientManager.add(TestModelsFactory.createTestClient2(addressList.get(0)));
		clientList.add(client2);

	}

	@After
	public void cleanup() throws Exception {

		for (Client client : clientList) {
			clientManager.delete(client);
		}
		
		for (Address address : addressList) {
			addressManager.delete(address);
		}
	}

	@Test
	public void getClient() throws Exception {
		
		Client testClient = clientManager.getAll().get(0);
				
		mockMvc.perform(get("/api/clients/" + testClient.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is( (int) testClient.getId() )))
				.andExpect(jsonPath("$.name", is(testClient.getName())))
				.andExpect(jsonPath("$.nip", is(testClient.getNIP())))
				.andExpect(jsonPath("$.bankAccountNumber", is(testClient.getBankAccountNumber())));		
	}
	
	@Test
	public void postClient() throws Exception {
		
		Client testClient = TestModelsFactory.createTestClient3(addressList.get(0));
		testClient.setId(clientList.get(1).getId() + 1);
		clientList.add(testClient);				

		mockMvc.perform(post("/api/clients")
				.contentType(contentType).content(convertObjectToJsonBytes(testClient)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is( (int) testClient.getId())))
				.andExpect(jsonPath("$.name", is(testClient.getName())))
				.andExpect(jsonPath("$.nip", is(testClient.getNIP())))
				.andExpect(jsonPath("$.bankAccountNumber", is(testClient.getBankAccountNumber())));				
	}
	
	@Test
	public void deleteClient() throws Exception {

		Client testClient = clientManager.add(TestModelsFactory.createTestClient3(addressList.get(0)));
		clientList.add(testClient);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/" + testClient.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		clientList.remove(testClient);
		Assert.assertFalse(clientManager.getAll().contains(testClient));
	}
	
	@Test
	public void getClients() throws Exception {

		Client testClient1 = clientManager.getAll().get(0);
		Client testClient2 = clientManager.getAll().get(1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/clients").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(clientManager.getAll().size())))
				.andExpect(jsonPath("$.[0].id", is( (int) testClient1.getId())))
				.andExpect(jsonPath("$.[0].name", is(testClient1.getName())))
				.andExpect(jsonPath("$.[0].nip", is(testClient1.getNIP())))
				.andExpect(jsonPath("$.[0].bankAccountNumber", is(testClient1.getBankAccountNumber())))
				.andExpect(jsonPath("$.[1].id", is( (int) testClient2.getId())))
				.andExpect(jsonPath("$.[1].name", is(testClient2.getName())))
				.andExpect(jsonPath("$.[1].nip", is(testClient2.getNIP())))
				.andExpect(jsonPath("$.[1].bankAccountNumber", is(testClient2.getBankAccountNumber())));
	}
	
	@Test
	public void updateClient() throws Exception {
		
		Client testClient = clientManager.add(TestModelsFactory.createTestClient3(addressList.get(0)));
		clientList.add(testClient);
		
		mockMvc.perform(post("/api/clients/" + testClient.getId()).contentType(contentType)
				.content(convertObjectToJsonBytes(testClient))
				).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.id", is( (int) testClient.getId())))
				.andExpect(jsonPath("$.name", is(testClient.getName())))
				.andExpect(jsonPath("$.nip", is(testClient.getNIP())))
				.andExpect(jsonPath("$.bankAccountNumber", is(testClient.getBankAccountNumber())));	

	}
	
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
