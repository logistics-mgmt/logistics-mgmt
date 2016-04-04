package com.jdbc.demo.web.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.domain.psql.Driver;
import com.jdbc.demo.MapsConfiguration;
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

	@Autowired
	FreightTransportDAO transportManager;

	@RequestMapping(method = RequestMethod.GET)
	public String getDrivers(ModelMap model) {
		model.addAttribute("drivers", driverManager.getAll());
		model.addAttribute("addresses", addressManager.getAll());	
		model.addAttribute("user", getPrincipal());
		return "drivers";
	}
	
	
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addDriver(ModelMap model) {
		model.addAttribute("addresses", addressManager.getAll());
		return "add_driver";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editDriver(@PathVariable long id, ModelMap model) {
		model.addAttribute("driver", driverManager.get(id));
		model.addAttribute("addresses", addressManager.getAll());
		return "edit_driver";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String driverDetails(@PathVariable long id, ModelMap model) {
		Driver driver = driverManager.get(id);
		model.addAttribute("driver", driver);
		model.addAttribute("addresses", addressManager.getAll());
		model.addAttribute("api_key", MapsConfiguration.getBrowserApiKey());
		model.addAttribute("on_road", transportManager.isDriverOnRoad(driver));
		return "driver_details";
	}
}