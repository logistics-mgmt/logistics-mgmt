package com.jdbc.demo.services.psql;

import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.ClientDAO;
import com.jdbc.demo.domain.psql.Address;
import com.jdbc.demo.domain.psql.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import utils.TestModelsFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 02-Nov-15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@Transactional(transactionManager = "txManager")
public class ClientManagerTest {
    
    @Autowired
    private ClientDAO clientManager;
    
    @Autowired
    private AddressDAO addressManager;
    
    private ArrayList<Client> testClients = new ArrayList<Client>();
    private ArrayList<Address> testAddresses = new ArrayList<Address>();

    @Before
    public void setUp() throws Exception {
        testAddresses.add(addressManager.add(TestModelsFactory.createTestAddress1()));
        testClients.add(TestModelsFactory.createTestClient1(testAddresses.get(0)));
        testClients.add(TestModelsFactory.createTestClient2(testAddresses.get(0)));
    }

    @Test
    public void testGetAll() throws Exception {

        Client client1 = clientManager.add(testClients.get(0));
        Client client2 = clientManager.add(testClients.get(1));

        List<Client> clients = clientManager.getAll();

        Assert.assertTrue(clients.size()>=2);
        Assert.assertTrue(clients.contains(client1));
        Assert.assertTrue(clients.contains(client2));

    }

    @Test
    public void testAdd() throws Exception {
        int sizeBeforeAddition = clientManager.getAll().size();
        Client client1 = clientManager.add(testClients.get(0));

        List<Client> clients = clientManager.getAll();

        Assert.assertTrue(clients.contains(client1));
        Assert.assertEquals(sizeBeforeAddition + 1, clients.size());
    }

    @Test
    public void testGetById() throws Exception {
        Client client1 = clientManager.add(testClients.get(0));

        Client foundClient1 = clientManager.get(client1.getId());

        Assert.assertEquals(foundClient1, client1);
    }

    @Test
    public void testUpdate() throws Exception {
        Client client1 = clientManager.add(testClients.get(0));

        client1.setName("DEF SA");
        clientManager.update(client1);

        Client updatedClient = clientManager.get(client1.getId());

        Assert.assertEquals(client1, updatedClient);
    }

    @Test
    public void testDelete() throws Exception {
        Client client1 = clientManager.add(testClients.get(0));
        Client client2 = clientManager.add(testClients.get(1));
        Assert.assertTrue(clientManager.getAll().contains(client1));

        clientManager.delete(client1.getId());

        List<Client> clients = clientManager.getAll();

        Assert.assertFalse(clients.contains(client1));
        Assert.assertTrue(clients.contains(client2));
    }
}