package com.jdbc.demo.services;

import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.domain.Driver;
import com.jdbc.demo.domain.FreightTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mateusz on 22-Oct-15.
 */
public class DriverEntityManager extends EntityManager implements DriverDAO {

    private final static Logger LOGGER = LoggerFactory.getLogger(DriverEntityManager.class);

    private PreparedStatement updateStatement;
    private PreparedStatement createStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getStatement;
    private PreparedStatement getAllStatement;
    private PreparedStatement getTransportsStatement;

    private AddressEntityManager addressEntityManager;

    public DriverEntityManager() {

        try {
            addressEntityManager = new AddressEntityManager();
            //freightTransportEntityManager = new FreightTransportEntityManager();

            Connection connection = DriverManager.getConnection(connectionString);
            ResultSet rs = connection.getMetaData().getTables(null, null, null,
                    null);
            boolean tableExists = false;

            while (rs.next()) {
                if ("Driver".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
                    tableExists = true;
                    break;
                }
            }

            if (!tableExists)
                throw new SQLException("Table Driver not found in database");

            createStatement = connection.prepareStatement("INSERT INTO Driver(id_Address, first_name, last_name," +
                    " pesel, salary, salary_bonus, available, deleted) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            deleteStatement = connection.prepareStatement("Delete FROM Driver WHERE id_Driver = ?");
            getAllStatement = connection.prepareStatement("SELECT * FROM Driver");
            getStatement = connection.prepareStatement("SELECT * FROM Driver WHERE id_Driver = ?");
            getTransportsStatement = connection.prepareStatement("SELECT * FROM FreightTransportDrivers WHERE id_Driver = ?");
            updateStatement = connection.prepareStatement("UPDATE Driver SET id_Address = ?, first_name = ?," +
                    " last_name = ?, pesel = ?, salary = ?, salary_bonus = ?, available = ?, deleted = ? WHERE id_Driver = ?");
        } catch (SQLException sqlE) {
            LOGGER.error("SQL Exception has been thrown during DriverEntityManager set up...", sqlE);
        }
    }

    public ArrayList<Driver> getAll() {
        ArrayList<Driver> drivers = new ArrayList<Driver>();

        try (ResultSet rs = getAllStatement.executeQuery()){
            while (rs.next()) {
                Driver driver = new Driver();
                driver.setId(rs.getInt("id_Driver"));
                driver.setAddress(addressEntityManager.get(rs.getInt("id_Address")));
                driver.setFirstName(rs.getString("first_name"));
                driver.setLastName(rs.getString("last_name"));
                driver.setPESEL(rs.getString("pesel"));
                driver.setSalary(rs.getBigDecimal("salary"));
                driver.setSalaryBonus(rs.getBigDecimal("salary_bonus"));
                driver.setAvailable(rs.getBoolean("available"));
                driver.setDeleted(rs.getBoolean("deleted"));

                drivers.add(driver);
            }

        } catch (SQLException sqlE) {
            LOGGER.error("SQL Exception has been thrown during query for Drivers...", sqlE);
            drivers = null;
        }

        return drivers;
    }

    public Driver add(Driver driver) {
        try {

            createStatement.setInt(1, driver.getAddress().getId());
            createStatement.setString(2, driver.getFirstName());
            createStatement.setString(3, driver.getLastName());
            createStatement.setString(4, driver.getPESEL());
            createStatement.setBigDecimal(5, driver.getSalary());
            createStatement.setBigDecimal(6, driver.getSalaryBonus());
            createStatement.setBoolean(7, driver.isAvailable());
            createStatement.setBoolean(8, driver.isDeleted());

            createStatement.executeUpdate();

            try(ResultSet generatedKeys = createStatement.getGeneratedKeys()){
                generatedKeys.next();
                driver.setId(generatedKeys.getInt(1));
                generatedKeys.close();
            }

        } catch (SQLException sqlE) {
            LOGGER.error(String.format("SQL Exception has been thrown while adding Driver %s ", driver), sqlE);
            return null;
        }
        LOGGER.info(String.format("Successfully added Driver:\n %s.", driver));
        return driver;
    }

    public Driver get(int id) {

        Driver driver = new Driver();

        try {
            getStatement.setInt(1, id);
            try(ResultSet rs = getStatement.executeQuery()){
                rs.next();
                driver.setId(rs.getInt("id_Driver"));
                driver.setAddress(addressEntityManager.get(rs.getInt("id_Address")));
                driver.setFirstName(rs.getString("first_name"));
                driver.setLastName(rs.getString("last_name"));
                driver.setPESEL(rs.getString("pesel"));
                driver.setSalary(rs.getBigDecimal("salary"));
                driver.setSalaryBonus(rs.getBigDecimal("salary_bonus"));
                driver.setAvailable(rs.getBoolean("available"));
                driver.setDeleted(rs.getBoolean("deleted"));
            }

        } catch (SQLException sqlE) {
            LOGGER.error(String.format("SQL Exception has been thrown while fetching Driver %s ", driver), sqlE);
            driver = null;
            LOGGER.error(String.format("Failed to get Driver with id: %d. SQLException: %s",id ,sqlE.getMessage()));
        }
        LOGGER.info(String.format("Query for Driver with id: %d returned:\n %s.",id, driver));
        return driver;
    }

    public void delete(int id) {

        try {
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (SQLException sqlE) {
            LOGGER.error(String.format("SQL Exception has been thrown while deleting Driver with id %d ", id), sqlE);
        }
        LOGGER.info(String.format("Successfully deleted Driver with id: %d.", id));
    }

    public void update(Driver driver) {
        try {
            updateStatement.setInt(1, driver.getAddress().getId());
            updateStatement.setString(2, driver.getFirstName());
            updateStatement.setString(3, driver.getLastName());
            updateStatement.setString(4, driver.getPESEL());
            updateStatement.setBigDecimal(5, driver.getSalary());
            updateStatement.setBigDecimal(6, driver.getSalaryBonus());
            updateStatement.setBoolean(7, driver.isAvailable());
            updateStatement.setBoolean(8, driver.isDeleted());

            updateStatement.setInt(9, driver.getId());

            updateStatement.executeUpdate();
        } catch (SQLException sqlE) {
            LOGGER.error(String.format("SQL Exception has been thrown while updating Driver %s ", driver), sqlE);
        }
        LOGGER.info(String.format("Successfully updated Driver:\n %s.", driver));
    }

    public ArrayList<FreightTransport> getTransports(int id, FreightTransportDAO freightTransportEntityManager) {

        ArrayList<FreightTransport> transports = new ArrayList<FreightTransport>();

        try{
            getTransportsStatement.setInt(1, id);

            try(ResultSet rs = getTransportsStatement.executeQuery()){
                while(rs.next()){
                    transports.add(freightTransportEntityManager.get(rs.getInt("id_FreightTransport")));
                }
            }
        }
        catch (SQLException e){
            LOGGER.error(String.format("SQL Exception has been thrown while getting transports for Driver with id: %d .", id), e);
            transports = null;
        }
        LOGGER.info(String.format("Successfully fetched transports for Driver with id: %d.", id));
        return transports;
    }
}
