package com.jdbc.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * Created by Mateusz on 02-Jan-16.
 */
public class MapsConfiguration {

	private final static String PROPERTIES_FILE_NAME = "maps.properties";
	private final static String SERVER_KEY_NAME = "googlemaps.serverKey";
	private final static String BROWSER_KEY_NAME = "googlemaps.browserKey";

	private static Logger LOGGER = LoggerFactory.getLogger(MapsConfiguration.class);
	private static Properties PROPERTIES;

	static {
		try {
			PROPERTIES = readProperties();
		} catch (FileNotFoundException ex) {
			LOGGER.error(String.format("Unable to initialize Google Maps Api configuration. Check if file %s exists"
					+ " and is properly formatted.", PROPERTIES_FILE_NAME), ex);
		}
	}

	public static String getServerApiKey() throws NoSuchElementException {
		return getPropertiesKey(SERVER_KEY_NAME);
	}

	public static String getBrowserApiKey() throws NoSuchElementException {
		return getPropertiesKey(BROWSER_KEY_NAME);
	}

	private static String getPropertiesKey(String keyName) throws NoSuchElementException {
		if (!PROPERTIES.containsKey(keyName))
			throw new NoSuchElementException(
					String.format("Required key %s not" + "found in Google Maps API configuration file(location: %s).",
							keyName, PROPERTIES_FILE_NAME));

		return PROPERTIES.getProperty(keyName);
	}

	private static Properties readProperties() throws FileNotFoundException {

		InputStream inputStream;
		Properties properties = new Properties();
		try {
			inputStream = MapsConfiguration.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);

			if (inputStream != null) {
				properties.load(inputStream);
				inputStream.close();
			} else {
				throw new FileNotFoundException(
						String.format("Unable to find %s file in classpath.", PROPERTIES_FILE_NAME));
			}
		} catch (IOException e) {
			LOGGER.error("Exception has been thrown during reading of Google Maps API configuration file.", e);
		}

		return properties;
	}

}
