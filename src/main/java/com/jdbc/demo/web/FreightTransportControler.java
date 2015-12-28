package com.jdbc.demo.web;

import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.domain.FreightTransport;
import com.jdbc.demo.telemetry.mongo.domain.RouteWaypoint;
import com.jdbc.demo.telemetry.mongo.services.RouteWaypointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Mateusz on 22-Dec-15.
 */

@Controller
@RequestMapping("/transports")
public class FreightTransportControler {

    @Autowired
    FreightTransportDAO transportManager;

    @Autowired
    RouteWaypointRepository routeRepository;

    private String buildWaypointsString(List<RouteWaypoint> route){
        StringBuilder builder = new StringBuilder();
        for (RouteWaypoint waypoint : route){
            builder.append(waypoint.getLocation());
            builder.append("|");
        }
        // return string without the trailing pipe
        if(builder.length()==0)
            return null;
        else
            return builder.substring(0, builder.length()-1);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET )
    public String getTransport(@PathVariable long id, ModelMap model){
        FreightTransport transport = transportManager.get(id);
        model.addAttribute("transport", transport);
        model.addAttribute("drivers", transport.getDrivers());
        model.addAttribute("vehicles", transport.getVehicles());
        model.addAttribute("route", routeRepository.findByFreightTransportId(id));
        model.addAttribute("waypointsString", buildWaypointsString(routeRepository.findByFreightTransportId(id)));
        return "transport_overview";
    }
}
