package com.jdbc.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Mateusz on 28-Oct-15.
 */
public abstract class ConfigurationParser {
    //TODO
    private final static String configurationFile="com.jdbc.demo.resources.config.properties";

    private final static Logger LOGGER = LoggerFactory.getLogger(ConfigurationParser.class);

    public static HashMap<String,String> getProperties(){
        try{
            Properties prop = new Properties();
            HashMap<String, String> properties = new HashMap<>();

            prop.load(ConfigurationParser.class.getClassLoader().getResourceAsStream(configurationFile));
            properties.put("dbType", prop.getProperty("dbType"));
            properties.put("dbHost", prop.getProperty("dbHost"));
            properties.put("dbName", prop.getProperty("dbName"));

            return properties;
        }
        catch (IOException e){
            LOGGER.error(String.format("Unable to load configuration file: %s .", configurationFile), e);
        }


        return null;
    }
}
