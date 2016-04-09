package com.jdbc.demo.services.planning;

import java.util.List;

import com.jdbc.demo.domain.psql.Driver;
import com.jdbc.demo.domain.psql.FreightTransport;
import com.jdbc.demo.services.planning.exceptions.PlanningException;

/**
 * Created by owen on 25/03/16.
 */
public interface DriverAnalyzer {
	List<Driver> getDriversForPlannedTransport(List<Driver> drivers, FreightTransport plannedTransport, int count)
			throws PlanningException;
}
