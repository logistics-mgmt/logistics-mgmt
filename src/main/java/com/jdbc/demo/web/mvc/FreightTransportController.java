package com.jdbc.demo.web.mvc;

import com.jdbc.demo.FreightTransportDAO;

import com.jdbc.demo.domain.psql.FreightTransport;
import com.jdbc.demo.MapsConfiguration;
import com.jdbc.demo.domain.mongo.Route;
import com.jdbc.demo.services.mongo.RouteWaypointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * Created by Bartosz on 23-Dec-15.
 */
@Controller
@RequestMapping("/transports")
public class FreightTransportController {
	private final static Logger LOGGER = LoggerFactory.getLogger(FreightTransportController.class);
	@Autowired
	FreightTransportDAO transportManager;

    @Autowired
    RouteWaypointRepository routeRepository;
	
	@RequestMapping(method = RequestMethod.GET )
    public String getTransports(ModelMap model){
        model.addAttribute("transports", transportManager.getAll());
        return "transports";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET )
    public String getTransport(@PathVariable long id, ModelMap model){
        FreightTransport transport = transportManager.get(id);
        model.addAttribute("transport", transport);
        model.addAttribute("drivers", transport.getDrivers());
        model.addAttribute("vehicles", transport.getVehicles());
        Route route = new Route(routeRepository.findByFreightTransportIdOrderByTimestampAsc(id));
        model.addAttribute("route", route);
        model.addAttribute("api_key", MapsConfiguration.getBrowserApiKey());
        model.addAttribute("waypointsString",
                route.getWaypointsString());
        return "transport_overview";
    }
}
