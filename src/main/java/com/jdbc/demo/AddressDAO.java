package com.jdbc.demo;

import com.jdbc.demo.domain.Address;

import java.util.List;

/**
 * Created by Mateusz on 31-Oct-15.
 */
public interface AddressDAO {
    List<Address> getAll();
    Address add(Address address);
    Address get(int id);
    void update(Address address);
    void delete(int id);
}
