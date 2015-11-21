package com.jdbc.demo.services;

import com.jdbc.demo.utils.ConfigurationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by Mateusz on 14-Nov-15.
 */
public class EntityManagerFactory {

    private String connectionString;

    private final static Logger LOGGER = LoggerFactory.getLogger(EntityManagerFactory.class);

    public EntityManagerFactory(String dbUser, String dbPass){
        this.connectionString = formConnectionString(dbUser, dbPass);
    }

    private static String formConnectionString(String dbUser, String dbPass){

        String connectionString = null;
        HashMap<String, String> configurationProperties = ConfigurationParser.getProperties();
        try{
            connectionString = String.format("jdbc:%s://%s;databaseName=%s,user=%s,password=%s;", configurationProperties.get("dbType"),
                    configurationProperties.get("dbHost"), configurationProperties.get("dbName"), dbUser, dbPass);
        }
        catch (NullPointerException e){
            LOGGER.error("Failed to form connection String, check if config.properties file contains dbType, dbHost and dbName entries.", e);
            connectionString = null;
        }

        return connectionString;

    }

    private static boolean checkConnectionString(String connectionString){
        try{
            DriverManager.getConnection(connectionString);
        }
        catch (SQLException e){
            return false;
        }
        return true;
    }
}
