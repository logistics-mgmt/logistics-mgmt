package com.jdbc.demo;

import com.jdbc.demo.domain.Driver;
import com.jdbc.demo.domain.FreightTransport;
import com.jdbc.demo.domain.Vehicle;

import java.util.List;

/**
 * Created by Mateusz on 23-Oct-15.
 */
public interface FreightTransportDAO {
    List<FreightTransport> getAll();
    FreightTransport add(FreightTransport freightTransport);
    void update(FreightTransport freightTransport);
    void delete(int id);
    FreightTransport get(int id);
    List<Driver> getDrivers(int id);
    List<Vehicle> getVehicles(int id);
}
