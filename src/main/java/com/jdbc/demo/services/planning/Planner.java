package com.jdbc.demo.services.planning;

import com.jdbc.demo.domain.psql.FreightTransport;

/**
 * Created by owen on 25/03/16.
 */
public interface Planner {
	FreightTransport planTransport(FreightTransport transport);
}
