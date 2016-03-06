package com.jdbc.demo.web.rest;

import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.domain.psql.FreightTransport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transports")
public class FreightTransportRestController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ClientRestController.class);

	@Autowired
	FreightTransportDAO transportManager;

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody List<FreightTransport> getFreightTransports() {
		return transportManager.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody FreightTransport getFreightTransport(@PathVariable long id) {
		return transportManager.get(id);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json; charset=UTF-8" }, produces = {
			"application/json; charset=UTF-8" })
	public @ResponseBody FreightTransport addFreightTransport(@RequestBody FreightTransport freightTransport) {

		LOGGER.info(String.format("Adding Freight Transport %s.", freightTransport));
		return transportManager.add(freightTransport);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteFreightTransport(@PathVariable("id") long id) {
		FreightTransport freightTransport = transportManager.get(id);
		LOGGER.info(String.format("Deleting Freight Transport %s.", freightTransport));
		transportManager.delete(freightTransport);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = {
			"application/json; charset=UTF-8" }, produces = { "application/json; charset=UTF-8" })

	public @ResponseBody FreightTransport updateFreightTransport(@RequestBody FreightTransport freightTransport) {
		LOGGER.info(String.format("Updating Freight Transport %s.", freightTransport));
		return transportManager.update(freightTransport);
	}

}
