package com.jdbc.demo.web;

import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.domain.FreightTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mateusz on 22-Dec-15.
 */

@Controller
@RequestMapping("/transports")
public class FreightTransportControler {

    @Autowired
    FreightTransportDAO transportManager;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET )
    public String getTransport(@PathVariable long id, ModelMap model){
        FreightTransport transport = transportManager.get(id);
        model.addAttribute("transport", transport);
        model.addAttribute("drivers", transport.getDrivers());
        model.addAttribute("vehicles", transport.getVehicles());
        return "transport_overview";
    }
}
