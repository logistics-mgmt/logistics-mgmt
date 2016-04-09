package com.jdbc.demo.services.planning.analyzers;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.jdbc.demo.domain.psql.FreightTransport;

/**
 * Created by owen on 25/03/16.
 */
public abstract class Analyzer {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Analyzer.class);

	protected boolean checkTimePeriodAvailability(List<FreightTransport> transports, Date start_date, Date end_date) {
		/**
		 * Checks if no transports are active during time period defined by
		 * given start and end dates.
		 *
		 *
		 */

		if(start_date == null || end_date == null)
			throw new IllegalArgumentException("Both start and end dates must be defined.");

		if(end_date.compareTo(start_date) < 0)
			throw new IllegalArgumentException("Wrong start and end dates order.");

		LOGGER.info(String.format("Checking transports activity betweeen %s and %s.", start_date, end_date));

		for (FreightTransport transport : transports) {

			// Check if transport loadDate lies between start_date and end_date
			if (transport.getLoadDate().compareTo(start_date) >= 0 &&
					transport.getLoadDate().compareTo(end_date) <= 0) {
				return false;
			}

			// Check if transport unloadDate lies between start_date and end_date
			if (transport.getUnloadDate() != null &&
					transport.getUnloadDate().compareTo(start_date) >= 0 &&
					transport.getUnloadDate().compareTo(end_date) <= 0) {
				return false;
			}

			// Check if transport time interval is overlapping
			// given start_date and end_date
			if (transport.getUnloadDate() != null &&
					transport.getLoadDate().compareTo(start_date) <= 0 &&
					transport.getUnloadDate().compareTo(end_date) >= 0) {
				return false;
			}

		}

		return true;
	}

}
