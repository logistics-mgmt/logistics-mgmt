package com.jdbc.demo.services.planning.analyzers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jdbc.demo.domain.psql.Driver;
import com.jdbc.demo.domain.psql.FreightTransport;
import com.jdbc.demo.services.planning.DriverAnalyzer;
import com.jdbc.demo.services.planning.exceptions.PlanningException;

/**
 * Created by owen on 25/03/16.
 */
@Service
public class BasicDriverAnalyzer extends Analyzer implements DriverAnalyzer {

	public List<Driver> getDriversForPlannedTransport(List<Driver> drivers, FreightTransport plannedTransport,
													  int count)
			throws PlanningException {

		List<Driver> suggestedDrivers = new ArrayList<>();

		int counter = 0;

		for (Driver driver : drivers) {
			if (isDriverAvailable(driver, plannedTransport.getLoadDate(), plannedTransport.getUnloadDate()) &&
					counter < count) {
				suggestedDrivers.add(driver);
				++counter;
			}
		}

		if (suggestedDrivers.isEmpty())
			throw new PlanningException(String.format("There are no available drivers for transport:\n %s.",
					plannedTransport));

		return suggestedDrivers;
	}

	private boolean isDriverAvailable(Driver driver, Date start_date, Date end_date) {
		return checkTimePeriodAvailability(driver.getTransports(), start_date, end_date);
	}
}
