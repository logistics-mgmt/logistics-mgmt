package com.jdbc.demo.services.planning.planners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.Distance;
import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.VehicleDAO;
import com.jdbc.demo.domain.psql.Address;
import com.jdbc.demo.domain.psql.FreightTransport;
import com.jdbc.demo.services.maps.DistanceService;
import com.jdbc.demo.services.planning.DriverAnalyzer;
import com.jdbc.demo.services.planning.Planner;
import com.jdbc.demo.services.planning.VehicleAnalyzer;
import com.jdbc.demo.services.planning.exceptions.PlanningException;

/**
 * Created by owen on 25/03/16.
 */
@Service
public class BasicPlanner implements Planner {

	@Autowired
	VehicleAnalyzer vehicleAnalyzer;

	@Autowired
	DriverAnalyzer driverAnalyzer;

	@Autowired
	VehicleDAO vehicleManager;

	@Autowired
	DriverDAO driverManager;

	private static Logger LOGGER = LoggerFactory.getLogger(BasicPlanner.class);



	@Override
	public FreightTransport planTransport(FreightTransport transport) {
		try {
			transport.setVehicles(vehicleAnalyzer.getVehiclesForPlannedTransport(vehicleManager.getAll(),
					transport));
			transport.setDrivers(driverAnalyzer.getDriversForPlannedTransport(driverManager.getAll(),
					transport, transport.getVehicles().size()));
			transport.setDistance((int) calculateRouteDistance(transport.getLoadAddress(),
					transport.getUnloadAddress()));
			return transport;
		} catch (PlanningException e) {
			LOGGER.error(String.format("Not enough resources for transport: %s.", transport), e);
		}

		return null;

	}

	/**
	 * Calculate planned transport route distance.
	 * @param loadAddress route start address
	 * @param unloadAddress route end address
	 * @return distance between loadAddress and unloadAddress in kilometers.
	 */
	protected double calculateRouteDistance(Address loadAddress, Address unloadAddress){
		if(loadAddress == null || unloadAddress == null)
			throw new IllegalArgumentException("Load address and unload address must be defined.");
		if(loadAddress.getTown() == null || unloadAddress.getTown() == null)
			throw new IllegalArgumentException("Load address and unload address locations must be defined.");

		Distance distance = DistanceService.getDistance(loadAddress.getTown(), unloadAddress.getTown());
		if(distance ==null)
			return 0;
		else
			return (distance.inMeters / 1000.0);
	}
}
