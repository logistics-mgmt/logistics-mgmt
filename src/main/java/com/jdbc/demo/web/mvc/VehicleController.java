package com.jdbc.demo.web.mvc;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.MapsConfiguration;
import com.jdbc.demo.VehicleDAO;
import com.jdbc.demo.domain.psql.Vehicle;

/**
 * Created by Mateusz on 06-Dec-15.
 */
@Controller
@RequestMapping("/vehicles")
public class VehicleController {

	private final static Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

	@Autowired
	VehicleDAO vehicleManager;

	@Autowired
	FreightTransportDAO transportManager;

	@RequestMapping(method = RequestMethod.GET)
	public String getVehicles(ModelMap model) {
		model.addAttribute("vehicles", vehicleManager.getAll());
		return "vehicles";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addVehicle(ModelMap model) {
		return "add_vehicle";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editVehicle(@PathVariable long id, ModelMap model) {
		model.addAttribute("vehicle", vehicleManager.get(id));
		return "edit_vehicle";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String vehicleDetails(@PathVariable long id, ModelMap model) {
		Vehicle vehicle = vehicleManager.get(id);
		model.addAttribute("vehicle", vehicleManager.get(id));
		model.addAttribute("api_key", MapsConfiguration.getBrowserApiKey());
		model.addAttribute("on_road", transportManager.isVehicleOnRoad(vehicle));

		return "vehicle_details";
	}

}
