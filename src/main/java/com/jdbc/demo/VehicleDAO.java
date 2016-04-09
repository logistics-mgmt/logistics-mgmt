package com.jdbc.demo;

import java.util.List;

import com.jdbc.demo.domain.psql.Vehicle;

/**
 * Created by mciesielski on 2015-10-23.
 */
public interface VehicleDAO {
	List<Vehicle> getAll();

	Vehicle add(Vehicle vehicle);

	Vehicle get(long id);

	Vehicle update(Vehicle vehicle);

	void delete(Vehicle vehicle);

	void delete(long id);
}
