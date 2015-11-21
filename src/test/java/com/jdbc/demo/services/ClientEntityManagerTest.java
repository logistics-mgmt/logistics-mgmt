package com.jdbc.demo.services;

import com.jdbc.demo.domain.Address;
import com.jdbc.demo.domain.Client;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.TestModelsFactory;

import java.util.ArrayList;

/**
 * Created by Mateusz on 02-Nov-15.
 */
public class ClientEntityManagerTest {

    private ClientEntityManager clientEntityManager = new ClientEntityManager();
    private AddressEntityManager addressEntityManager = new AddressEntityManager();
    private ArrayList<Client> testClients = new ArrayList<Client>();
    private ArrayList<Address> testAddresses = new ArrayList<Address>();

    @Before
    public void setUp() throws Exception {
        testAddresses.add(addressEntityManager.add(TestModelsFactory.createTestAddress1()));
        testClients.add(TestModelsFactory.createTestClient1(testAddresses.get(0)));
        testClients.add(TestModelsFactory.createTestClient2(testAddresses.get(0)));
    }

    @After
    public void tearDown() throws Exception {
        for (Client testClient : testClients) {
            clientEntityManager.delete(testClient.getId());
        }
        for (Address testAddress : testAddresses) {
            addressEntityManager.delete(testAddress.getId());
        }
    }

    @Test
    public void testGetAll() throws Exception {

        Client client1 = clientEntityManager.add(testClients.get(0));
        Client client2 = clientEntityManager.add(testClients.get(1));


        Assert.assertTrue(clientEntityManager.getAll().size()>=2);
        Assert.assertTrue(clientEntityManager.getAll().contains(client1));
        Assert.assertTrue(clientEntityManager.getAll().contains(client2));

    }

    @Test
    public void testAdd() throws Exception {
        int sizeBeforeAddition = clientEntityManager.getAll().size();
        Client client1 = clientEntityManager.add(testClients.get(0));

        Assert.assertTrue(clientEntityManager.getAll().contains(client1));
        Assert.assertEquals(sizeBeforeAddition + 1, clientEntityManager.getAll().size());
    }

    @Test
    public void testGetById() throws Exception {
        Client client1 = clientEntityManager.add(testClients.get(0));

        Client foundClient1 = clientEntityManager.get(client1.getId());

        Assert.assertEquals(foundClient1, client1);
    }

    @Test
    public void testGetByName() throws Exception {
        Client client1 = clientEntityManager.add(testClients.get(0));

        Client foundClient1 = clientEntityManager.get(client1.getName());

        Assert.assertEquals(client1, foundClient1);
    }

    @Test
    public void testUpdate() throws Exception {
        Client client1 = clientEntityManager.add(testClients.get(0));

        client1.setName("DEF SA");
        clientEntityManager.update(client1);

        Client updatedClient = clientEntityManager.get(client1.getId());

        Assert.assertEquals(client1, updatedClient);
    }

    @Test
    public void testDelete() throws Exception {
        Client client1 = clientEntityManager.add(testClients.get(0));
        Client client2 = clientEntityManager.add(testClients.get(1));
        Assert.assertTrue(clientEntityManager.getAll().contains(client1));

        clientEntityManager.delete(client1.getId());

        Assert.assertFalse(clientEntityManager.getAll().contains(client1));
        Assert.assertTrue(clientEntityManager.getAll().contains(client2));
    }
}