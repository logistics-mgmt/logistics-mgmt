package com.jdbc.demo.web;

import com.jdbc.demo.VehicleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Mateusz on 06-Dec-15.
 */
@Controller
@RequestMapping("/vehicles")
public class VehicleController {


    private final static Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    VehicleDAO vehicleManager;

    @RequestMapping(method = RequestMethod.GET )
    public String getVehicles(ModelMap model){
        model.addAttribute("vehicles", vehicleManager.getAll());
        return "vehicles";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addVehicle(ModelMap model){
        return "add_vehicle";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editVehicles(@PathVariable long id, ModelMap model){
        model.addAttribute("vehicle", vehicleManager.get(id));
        return "edit_vehicle";
    }


}
