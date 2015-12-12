package com.jdbc.demo.web;

import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.DriverDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mateusz on 30-Nov-15.
 */

@Controller
@RequestMapping("/drivers")
public class DriverController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DriverController.class);

    @Autowired
    DriverDAO driverManager;

    @Autowired
    AddressDAO addressManager;

    @RequestMapping(method = RequestMethod.GET )
    public String getDrivers(ModelMap model){
        model.addAttribute("drivers", driverManager.getAll());
        model.addAttribute("addresses", addressManager.getAll());
        return "drivers";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addDriver(ModelMap model){
        return "add_driver";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editDriver(@PathVariable long id, ModelMap model){
        model.addAttribute("driver", driverManager.get(id));
        model.addAttribute("addresses", addressManager.getAll());
        return "edit_driver";
    }
}
