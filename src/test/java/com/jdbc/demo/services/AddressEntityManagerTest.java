package com.jdbc.demo.services;

import com.jdbc.demo.domain.Address;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.TestModelsFactory;

import java.util.ArrayList;

/**
 * Created by Mateusz on 03-Nov-15.
 */
public class AddressEntityManagerTest {

    private AddressEntityManager addressEntityManager = new AddressEntityManager();
    private ArrayList<Address> testAddresses = new ArrayList<Address>();

    @Before
    public void setUp() throws Exception {
        testAddresses.add(TestModelsFactory.createTestAddress1());
        testAddresses.add(TestModelsFactory.createTestAddress2());
    }

    @After
    public void tearDown() throws Exception {
        for (Address testAddress: testAddresses){
            addressEntityManager.delete(testAddress.getId());
        }
    }

    @Test
    public void testGetAll() throws Exception {
        addressEntityManager.add(testAddresses.get(0));
        addressEntityManager.add(testAddresses.get(1));

        Assert.assertTrue(addressEntityManager.getAll().size()>=2);
        Assert.assertTrue(addressEntityManager.getAll().contains(testAddresses.get(0)));
        Assert.assertTrue(addressEntityManager.getAll().contains(testAddresses.get(1)));
    }

    @Test
    public void testAdd() throws Exception {
        int sizeBeforeAddition = addressEntityManager.getAll().size();
        addressEntityManager.add(testAddresses.get(0));

        Assert.assertTrue(addressEntityManager.getAll().contains(testAddresses.get(0)));
        Assert.assertEquals(sizeBeforeAddition+1, addressEntityManager.getAll().size());
    }

    @Test
    public void testGet() throws Exception {
        Address address = addressEntityManager.add(testAddresses.get(0));

        Assert.assertEquals(address, addressEntityManager.get(address.getId()));
    }

    @Test
    public void testDelete() throws Exception {
        Address address = addressEntityManager.add(testAddresses.get(0));
        Address address2 = addressEntityManager.add(testAddresses.get(1));
        addressEntityManager.delete(address.getId());

        Assert.assertFalse(addressEntityManager.getAll().contains(address));
        Assert.assertTrue(addressEntityManager.getAll().contains(address2));
    }

    @Test
    public void testUpdate() throws Exception {
        Address address = addressEntityManager.add(testAddresses.get(0));
        address.setTown("Kielce");
        addressEntityManager.update(address);

        Assert.assertEquals(address, addressEntityManager.get(address.getId()));
    }
}