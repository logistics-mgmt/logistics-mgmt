package com.jdbc.demo.web;

import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.domain.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping(method = RequestMethod.GET, params = {"!id"} )
    public String getDrivers(ModelMap model){
        model.addAttribute("drivers", driverManager.getAll());
        model.addAttribute("addresses", addressManager.getAll());
        return "drivers";
    }

    //TODO: Use ModelAttribute instead of params list for form requests handling.
    @RequestMapping(method = RequestMethod.POST, params = {"!id"} )
    public String addDriver(@RequestParam("first-name")String firstName, @RequestParam("last-name")String lastName,
                            @RequestParam("pesel")String pesel, @RequestParam("available")Boolean available,
                            @RequestParam("address-id")long addressId, ModelMap model){

        Driver driver = new Driver();
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setPESEL(pesel);
        driver.setAvailable(available);
        driver.setAddress(addressManager.get(addressId));
        LOGGER.info(String.format("Adding driver %s.", driver));
        driverManager.add(driver);

        model.addAttribute("drivers", driverManager.getAll());
        model.addAttribute("addresses", addressManager.getAll());
        return "drivers";
    }

    //TODO: Use ModelAttribute instead of params list for form requests handling.
    @RequestMapping(method = RequestMethod.POST, params = {"id"} )
    public String updateDriver(@RequestParam("id")long id, @RequestParam("first-name")String firstName,
                               @RequestParam("last-name")String lastName, @RequestParam("pesel")String pesel,
                               @RequestParam("available")Boolean available, @RequestParam("address-id")long addressId,
                               ModelMap model){

        Driver driver = driverManager.get(id);
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setPESEL(pesel);
        driver.setAvailable(available);
        driver.setAddress(addressManager.get(addressId));
        LOGGER.info(String.format("Updating driver %s.", driver));
        driverManager.update(driver);

        model.addAttribute("drivers", driverManager.getAll());
        model.addAttribute("addresses", addressManager.getAll());
        return "drivers";
    }

    //TODO: Use ModelAttribute instead of params list for form requests handling.
    @RequestMapping(method = RequestMethod.POST, params = {"id", "delete"} )
    public String deleteDriver(@RequestParam("id")long id, ModelMap model){

        Driver driver = driverManager.get(id);
        LOGGER.info(String.format("Deleting driver %s.", driver));
        driverManager.delete(driver);

        model.addAttribute("drivers", driverManager.getAll());
        model.addAttribute("addresses", addressManager.getAll());
        return "drivers";
    }

    @RequestMapping("/{id}")
    public String getDriver(@RequestParam(value = "id") long id, ModelMap model){
        return  "driver";
    }
}
