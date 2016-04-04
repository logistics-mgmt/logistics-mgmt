package com.jdbc.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Mateusz on 02-Jan-16.
 */
public class MapsConfiguration {

	public static String getServerApiKey() throws NoSuchElementException {
		return System.getenv("GOOGLE_MAPS_SERVER_KEY");
	}

	public static String getBrowserApiKey() throws NoSuchElementException {
		return System.getenv("GOOGLE_MAPS_BROWSER_KEY");
	}

}
