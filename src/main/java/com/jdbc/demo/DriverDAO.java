package com.jdbc.demo;

import java.util.List;

import com.jdbc.demo.domain.psql.Driver;

public interface DriverDAO {

	List<Driver> getAll();

	Driver add(Driver driver);

	Driver get(long id);

	Driver update(Driver driver);

	void delete(long id);
	
	void delete(String PESEL);

	void delete(Driver driver);

}
