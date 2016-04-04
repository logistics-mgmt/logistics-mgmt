package com.jdbc.demo.services.mongo;

import com.jdbc.demo.domain.mongo.RouteWaypoint;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Mateusz on 28-Dec-15.
 */
public interface RouteWaypointRepository extends MongoRepository<RouteWaypoint, String> {
	List<RouteWaypoint> findByDriverIdOrderByTimestampDesc(long driverId);

	List<RouteWaypoint> findByVehicleId(long vehicleId);

	List<RouteWaypoint> findByFreightTransportIdOrderByTimestampAsc(long freightTransportId);

	List<RouteWaypoint> findByFreightTransportIdAndVehicleId(long freightTransportId, long vehicleId);

	List<RouteWaypoint> findByFreightTransportIdAndDriverId(long freightTransportId, long driverId);
}
