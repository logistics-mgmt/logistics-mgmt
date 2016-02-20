package com.jdbc.demo.persistence.mongo;

import com.jdbc.demo.domain.mongo.RouteWaypoint;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Mateusz on 28-Dec-15.
 */
public interface RouteWaypointRepository extends MongoRepository<RouteWaypoint, String> {
    public List<RouteWaypoint> findByDriverIdOrderByTimestampDesc(long driverId);
    public List<RouteWaypoint> findByVehicleId(long vehicleId);
    public List<RouteWaypoint> findByFreightTransportIdOrderByTimestampAsc(long freightTransportId);
    public List<RouteWaypoint> findByFreightTransportIdAndVehicleId(long freightTransportId, long vehicleId);
    public List<RouteWaypoint> findByFreightTransportIdAndDriverId(long freightTransportId, long driverId);
}
