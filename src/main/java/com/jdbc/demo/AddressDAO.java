package com.jdbc.demo;

import java.util.List;

import com.jdbc.demo.domain.psql.Address;

/**
 * Created by Mateusz on 31-Oct-15.
 */
public interface AddressDAO {
	List<Address> getAll();

	Address add(Address address);

	Address get(long id);

	Address update(Address address);

	void delete(long id);

	void delete(Address address);
}
