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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.Application;
import com.jdbc.demo.ClientDAO;
import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.VehicleDAO;
import com.jdbc.demo.domain.psql.Address;
import com.jdbc.demo.domain.psql.Client;
import com.jdbc.demo.domain.psql.Driver;
import com.jdbc.demo.domain.psql.FreightTransport;
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
public class FreightTransportTest {

	private MockMvc mockMvc;

	@Autowired
	private AddressDAO addressManager;
	
	@Autowired
	private DriverDAO driverManager;
	
	@Autowired
	private VehicleDAO vehicleManager;
	
	@Autowired
	private ClientDAO clientManager;
	
	@Autowired
	private FreightTransportDAO FreightTransportManager;

	private ArrayList<Address> addressList = new ArrayList<Address>();
	private ArrayList<Driver> driverList = new ArrayList<Driver>();
	private ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
	private ArrayList<Client> clientList = new ArrayList<Client>();
	private ArrayList<FreightTransport> FreightTransportList = new ArrayList<FreightTransport>();

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void setup() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		addressList.add(addressManager.add(TestModelsFactory.createTestAddress1()));
		addressList.add(addressManager.add(TestModelsFactory.createTestAddress2()));

		driverList.add(driverManager.add(TestModelsFactory.createTestDriver1(addressList.get(0))));
		driverList.add(driverManager.add(TestModelsFactory.createTestDriver2(addressList.get(0))));

		vehicleList.add(vehicleManager.add(TestModelsFactory.createTestVehicle1()));
		vehicleList.add(vehicleManager.add(TestModelsFactory.createTestVehicle2()));

		clientList.add(clientManager.add(TestModelsFactory.createTestClient1(addressList.get(0))));
		clientList.add(clientManager.add(TestModelsFactory.createTestClient2(addressList.get(0))));
	
		//FreightTransportList.add(FreightTransportManager.add(TestModelsFactory.createTestFreightTransport1(clientList.get(0),driverList,
		//		vehicleList, addressList.get(0), addressList.get(1))));		
		//FreightTransportList.add(FreightTransportManager.add(TestModelsFactory.createTestFreightTransport2(clientList.get(0),driverList,
		//	vehicleList, addressList.get(1), addressList.get(0))));		
		
	}

	//@After
	public void cleanup() throws Exception {
		
		//FreightTransportList.remove(1);
		
		for (FreightTransport freightTransport : FreightTransportList) {
			FreightTransportManager.delete(freightTransport.getId());
		}		
		
		for (Client client : clientList) {
			clientManager.delete(client);
		}
		
	}
	//dziala
	//@Test
	public void getFreightTransport() throws Exception {
		
		 FreightTransportList.add(FreightTransportManager.add(TestModelsFactory.createTestFreightTransport1(clientList.get(0),driverList,
						vehicleList, addressList.get(0), addressList.get(1))));	
		
		
		FreightTransport testFreightTransport  = FreightTransportManager.getAll().get(0);
		
		mockMvc.perform(get("/api/transports/"+testFreightTransport.getId()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType));		
	}		
	
	
	//@Test
	public void postFreightTransport() throws Exception {
		
		FreightTransport testFreightTransport = TestModelsFactory.createTestFreightTransport1(clientList.get(0),driverList,
				vehicleList, addressList.get(1), addressList.get(0));
		testFreightTransport.setId(addressList.get(1).getId() + 2);
		//FreightTransportList.add(testFreightTransport);
		
		mockMvc.perform(post("/api/transports")
				.contentType(contentType).content(convertObjectToJsonBytes(testFreightTransport)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType));
	}
	
	//dziala
	//@Test
	public void deleteFreightTransport() throws Exception {

		 FreightTransportList.add(FreightTransportManager.add(TestModelsFactory.createTestFreightTransport1(clientList.get(0),driverList,
					vehicleList, addressList.get(0), addressList.get(1))));	
		
		
		FreightTransport testFreightTransport = FreightTransportManager.getAll().get(0);
		//FreightTransportList.add(testFreightTransport);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/transports/" + testFreightTransport.getId())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		//FreightTransportList.remove(testFreightTransport);
		//Assert.assertFalse(FreightTransportManager.getAll().contains(testFreightTransport));
	}
	
	@Test
	public void getFreightTransports() throws Exception {

		//FreightTransport testFreightTransport1 = FreightTransportManager.getAll().get(0);
		//FreightTransport testFreightTransport2 =FreightTransportManager.getAll().get(1);
		
		 FreightTransportList.add(FreightTransportManager.add(TestModelsFactory.createTestFreightTransport1(clientList.get(0),driverList,
					vehicleList, addressList.get(0), addressList.get(1))));
		 
		//FreightTransportManager.add(TestModelsFactory.createTestFreightTransport2(clientList.get(0),driverList,
		//			vehicleList, addressList.get(1), addressList.get(0)));
		
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/transports").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$", hasSize(FreightTransportManager.getAll().size())));
	}
	
	//@Test
	public void updateFreightTransport() throws Exception {
		
		FreightTransport testFreightTransport = FreightTransportManager.add(TestModelsFactory.createTestFreightTransport2(clientList.get(0),driverList,
				vehicleList, addressList.get(1), addressList.get(0)));
		
		mockMvc.perform(post("/api/transports/" + testFreightTransport.getId()).contentType(contentType)
				.content(convertObjectToJsonBytes(testFreightTransport))
				).andExpect(status().isOk())
				.andExpect(content().contentType(contentType));	

	}
	
	
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
