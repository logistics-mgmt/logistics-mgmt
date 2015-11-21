package com.jdbc.demo.services;

import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.domain.*;
import com.jdbc.demo.domain.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mateusz on 23-Oct-15.
 */
public class FreightTransportEntityManager extends EntityManager implements FreightTransportDAO {

    public ClientEntityManager clientEntityManager;
    public DriverEntityManager driverEntityManager;
    public VehicleEntityManager vehicleEntityManager;
    public AddressEntityManager addressEntityManager;

    private final static Logger LOGGER = LoggerFactory.getLogger(FreightTransportEntityManager.class);

    private PreparedStatement updateStatement;
    private PreparedStatement createStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getStatement;
    private PreparedStatement getAllStatement;
    private PreparedStatement getDriversStatement;
    private PreparedStatement getVehiclesStatement;
    private PreparedStatement addDriversStatement;
    private PreparedStatement addVehiclesStatement;
    private PreparedStatement deleteDriversStatement;
    private PreparedStatement deleteVehiclesStatement;

    public FreightTransportEntityManager() {

        try {

            clientEntityManager = new ClientEntityManager();
            driverEntityManager = new DriverEntityManager();
            vehicleEntityManager = new VehicleEntityManager();
            addressEntityManager = new AddressEntityManager();

            Connection connection = DriverManager.getConnection(connectionString);
            ResultSet rs = connection.getMetaData().getTables(null, null, null,
                    null);
            boolean tableExists = false;

            while (rs.next()) {
                if ("FreightTransport".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }

            if (!tableExists)
                throw new SQLException("Table FreightTransport not found in database");

            createStatement = connection.prepareStatement("INSERT INTO FreightTransport(id_load_Address, id_unload_Address, id_Client," +
                    " value, distance, load_date, unload_date, finished) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            deleteStatement = connection.prepareStatement("Delete FROM FreightTransport WHERE id_FreightTransport = ?");
            getAllStatement = connection.prepareStatement("SELECT * FROM FreightTransport");
            getStatement = connection.prepareStatement("SELECT * FROM FreightTransport WHERE id_FreightTransport = ?");
            getDriversStatement = connection.prepareStatement("SELECT id_Driver FROM FreightTransportDrivers WHERE id_FreightTransport = ?");
            getVehiclesStatement = connection.prepareStatement("SELECT id_Vehicle FROM FreightTransportVehicles WHERE id_FreightTransport = ?");
            updateStatement = connection.prepareStatement("UPDATE FreightTransport SET id_load_Address = ?, id_unload_Address = ?," +
                    " id_Client = ?, value = ?, distance = ?, load_date = ?, unload_date = ?, finished = ? WHERE id_FreightTransport = ?");
            addDriversStatement = connection.prepareStatement("INSERT INTO FreightTransportDrivers(id_FreightTransport, id_Driver) VALUES (?,?)");
            addVehiclesStatement = connection.prepareStatement("INSERT INTO FreightTransportVehicles(id_FreightTransport, id_Vehicle) VALUES (?,?)");
            deleteVehiclesStatement = connection.prepareStatement("DELETE FROM FreightTransportVehicles WHERE id_FreightTransport = ?");
            deleteDriversStatement = connection.prepareStatement("DELETE FROM FreightTransportDrivers WHERE id_FreightTransport = ?");
        } catch (SQLException sqlE) {
            LOGGER.error("SQL Exception has been thrown during FreightTransportEntityManager set up...", sqlE);
        }
    }

    public ArrayList<FreightTransport> getAll() {
        ArrayList<FreightTransport> freightTransports = new ArrayList<FreightTransport>();

        try (ResultSet rs = getAllStatement.executeQuery()){

            while (rs.next()) {
                FreightTransport freightTransport = new FreightTransport();
                freightTransport.setId(rs.getInt("id_FreightTransport"));
                freightTransport.setClient(clientEntityManager.get(rs.getInt("id_Client")));
                freightTransport.setLoadAddress(addressEntityManager.get(rs.getInt("id_load_Address")));
                freightTransport.setUnloadAddress(addressEntityManager.get(rs.getInt("id_unload_Address")));
                freightTransport.setDistance(rs.getInt("distance"));
                freightTransport.setValue(rs.getBigDecimal("value"));
                freightTransport.setLoadDate(rs.getDate("load_date"));
                freightTransport.setUnloadDate(rs.getDate("unload_date"));
                freightTransport.setFinished(rs.getBoolean("finished"));
                freightTransport.setDrivers(getDrivers(rs.getInt("id_FreightTransport")));
                freightTransport.setVehicles(getVehicles(rs.getInt("id_FreightTransport")));

                freightTransports.add(freightTransport);
            }
        } catch (SQLException sqlE) {
            LOGGER.error("SQL Exception has been thrown during query for FreightTransports...", sqlE);
            freightTransports = null;
        }

        return freightTransports;
    }

    public FreightTransport add(FreightTransport freightTransport) {

        try {
            createStatement.setInt(1, freightTransport.getLoadAddress().getId());
            createStatement.setInt(2, freightTransport.getUnloadAddress().getId());
            createStatement.setInt(3, freightTransport.getClient().getId());
            createStatement.setBigDecimal(4, freightTransport.getValue());
            createStatement.setInt(5, freightTransport.getDistance());
            createStatement.setDate(6, freightTransport.getLoadDate());
            createStatement.setDate(7, freightTransport.getUnloadDate());
            createStatement.setBoolean(8, freightTransport.getFinished());

            createStatement.executeUpdate();

            try(ResultSet generatedKeys = createStatement.getGeneratedKeys()){
                generatedKeys.next();
                freightTransport.setId(generatedKeys.getInt(1));
            }


            //Add FreightTransportDrivers and FreightTransportVehicles keys
            for (Vehicle vehicle: freightTransport.getVehicles()){
                addVehiclesStatement.setInt(1, freightTransport.getId());
                addVehiclesStatement.setInt(2, vehicle.getId());

                addVehiclesStatement.executeUpdate();
            }
            for (Driver driver: freightTransport.getDrivers()){
                addDriversStatement.setInt(1, freightTransport.getId());
                addDriversStatement.setInt(2, driver.getId());

                addDriversStatement.executeUpdate();
            }

        } catch (SQLException sqlE) {
            LOGGER.error(String.format("SQL Exception has been thrown while adding FreightTransport %s ", freightTransport), sqlE);
            return null;
        }

        return freightTransport;
    }

    public void update(FreightTransport freightTransport) {
        try {

            updateStatement.setInt(1, freightTransport.getLoadAddress().getId());
            updateStatement.setInt(2, freightTransport.getUnloadAddress().getId());
            updateStatement.setInt(3, freightTransport.getClient().getId());
            updateStatement.setBigDecimal(4, freightTransport.getValue());
            updateStatement.setInt(5, freightTransport.getDistance());
            updateStatement.setDate(6, freightTransport.getLoadDate());
            updateStatement.setDate(7, freightTransport.getUnloadDate());
            updateStatement.setBoolean(8, freightTransport.getFinished());

            updateStatement.setInt(9, freightTransport.getId());

            updateStatement.executeUpdate();

        } catch (SQLException sqlE) {
            LOGGER.error(String.format("SQL Exception has been thrown while updating FreightTransport %s ", freightTransport), sqlE);
        }

    }

    public void delete(int id) {
        try {

            deleteDriversStatement.setInt(1, id);
            deleteDriversStatement.executeUpdate();

            deleteVehiclesStatement.setInt(1, id);
            deleteVehiclesStatement.executeUpdate();

            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error(String.format("SQL Exception has been thrown while deleting FreightTransport with id %d ", id), e);
        }
    }

    public FreightTransport get(int id) {

        FreightTransport freightTransport = new FreightTransport();

        try {
            getStatement.setInt(1, id);
            try(ResultSet rs = getStatement.executeQuery()){
                rs.next();
                freightTransport.setId(rs.getInt("id_FreightTransport"));
                freightTransport.setClient(clientEntityManager.get(rs.getInt("id_Client")));
                freightTransport.setLoadAddress(addressEntityManager.get(rs.getInt("id_load_Address")));
                freightTransport.setUnloadAddress(addressEntityManager.get(rs.getInt("id_unload_Address")));
                freightTransport.setDistance(rs.getInt("distance"));
                freightTransport.setValue(rs.getBigDecimal("value"));
                freightTransport.setLoadDate(rs.getDate("load_date"));
                freightTransport.setUnloadDate(rs.getDate("unload_date"));
                freightTransport.setFinished(rs.getBoolean("finished"));
                freightTransport.setDrivers(getDrivers(rs.getInt("id_FreightTransport")));
                freightTransport.setVehicles(getVehicles(rs.getInt("id_FreightTransport")));
            }

        } catch (SQLException sqlE) {
            LOGGER.error(String.format("SQL Exception has been thrown while fetching FreightTransport %s ", freightTransport), sqlE);
            freightTransport = null;
        }

        return freightTransport;
    }

    public List<Driver> getDrivers(int id) {

        ArrayList<Driver> drivers = new ArrayList<Driver>();

        try{
            getDriversStatement.setInt(1, id);
            try(ResultSet rs = getDriversStatement.executeQuery()){
                while(rs.next()){
                    drivers.add(driverEntityManager.get(rs.getInt("id_Driver")));
                }
            }
        }
        catch (SQLException e){
            LOGGER.error(String.format("SQL Exception has been thrown while getting Drivers for FreightTransport %d ", id), e);
            drivers = null;
        }

        return drivers;
    }

    public List<Vehicle> getVehicles(int id) {

        ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

        try{
            getVehiclesStatement.setInt(1, id);
            try(ResultSet rs = getVehiclesStatement.executeQuery()){
                while(rs.next()){
                    vehicles.add(vehicleEntityManager.get(rs.getInt("id_Vehicle")));
                }
            }

        }
        catch (SQLException e){
            LOGGER.error(String.format("SQL Exception has been thrown while getting Vehicles for FreightTransport %d ", id), e);
            vehicles = null;
        }

        return vehicles;
    }
}
