package com.jdbc.demo.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.domain.psql.FreightTransport;
import com.jdbc.demo.services.maps.model.MappedRoute;
import com.jdbc.demo.services.planning.Planner;

@RestController
@RequestMapping("/api/transports")
public class FreightTransportRestController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ClientRestController.class);

	@Autowired
	FreightTransportDAO transportManager;

	@Autowired
	Planner transportPlanner;

	@RequestMapping(method = RequestMethod.GET, produces={"application/json; charset=UTF-8"})
	public @ResponseBody
	List<FreightTransport> getTransports(@RequestParam(name="active", required=false, defaultValue="false")
										 boolean active){
		if(active){
			LOGGER.info("GET active transports.");
			return transportManager.getAllActive();
		}
		else{
			LOGGER.info("GET all transports.");
			return transportManager.getAll();
		}
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

	@RequestMapping(value = "/{id}/route", method = RequestMethod.GET,
			produces={"application/json; charset=UTF-8"})
	public @ResponseBody
	MappedRoute getRoute(@PathVariable("id") long id){
		return transportManager.get(id).getRoute();
	}


	@RequestMapping(value= "/plan", method = RequestMethod.POST,
	consumes = {"application/json; charset=UTF-8"}, produces = {"application/json; charset=UTF-8"})
	public @ResponseBody FreightTransport planTransport(@RequestBody FreightTransport tranportPlan){
		return transportPlanner.planTransport(tranportPlan);
	}
}
