package com.jdbc.demo.services;

import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.domain.Address;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import utils.TestModelsFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 03-Nov-15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class AddressEntityManagerTest {

    @Autowired
    private AddressDAO addressManager;
    
    private ArrayList<Address> testAddresses = new ArrayList<Address>();

    @Before
    public void setUp() throws Exception {
        testAddresses.add(TestModelsFactory.createTestAddress1());
        testAddresses.add(TestModelsFactory.createTestAddress2());
    }

    @Test
    public void testGetAll() throws Exception {
        addressManager.add(testAddresses.get(0));
        addressManager.add(testAddresses.get(1));

        List<Address> addresses = addressManager.getAll();
        Assert.assertTrue(addresses.size()>=2);
        Assert.assertTrue(addresses.contains(testAddresses.get(0)));
        Assert.assertTrue(addresses.contains(testAddresses.get(1)));
    }

    @Test
    public void testAdd() throws Exception {
        int sizeBeforeAddition = addressManager.getAll().size();
        addressManager.add(testAddresses.get(0));
        List<Address> addresses = addressManager.getAll();
        Assert.assertTrue(addresses.contains(testAddresses.get(0)));
        Assert.assertEquals(sizeBeforeAddition+1, addresses.size());
    }

    @Test
    public void testGet() throws Exception {
        Address address = addressManager.add(testAddresses.get(0));

        Assert.assertEquals(address, addressManager.get(address.getId()));
    }

    @Test
    public void testDelete() throws Exception {
        Address address = addressManager.add(testAddresses.get(0));
        Address address2 = addressManager.add(testAddresses.get(1));
        addressManager.delete(address.getId());
        List<Address> addresses = addressManager.getAll();
        Assert.assertFalse(addresses.contains(address));
        Assert.assertTrue(addresses.contains(address2));
    }

    @Test
    public void testUpdate() throws Exception {
        Address address = addressManager.add(testAddresses.get(0));
        address.setTown("Kielce");
        addressManager.update(address);
        Assert.assertEquals(address, addressManager.get(address.getId()));
    }
}