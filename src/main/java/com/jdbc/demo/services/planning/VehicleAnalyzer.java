package com.jdbc.demo.services.planning;

import java.util.List;

import com.jdbc.demo.domain.psql.FreightTransport;
import com.jdbc.demo.domain.psql.Vehicle;
import com.jdbc.demo.services.planning.exceptions.PlanningException;

/**
 * Created by owen on 08/03/16.
 */
public interface VehicleAnalyzer {
    List<Vehicle> getVehiclesForPlannedTransport(List<Vehicle> vehicles, FreightTransport plannedTransport)
            throws PlanningException;
}
