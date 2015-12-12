package com.jdbc.demo.web;

import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.domain.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Mateusz on 12-Dec-15.
 */

@RestController
@RequestMapping("/api/drivers")
public class DriverRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DriverRestController.class);

    @Autowired
    DriverDAO driverManager;

    @RequestMapping(method = RequestMethod.GET, produces={"application/json; charset=UTF-8"})
    public @ResponseBody
    List<Driver> getDrivers(){
        return  driverManager.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces={"application/json; charset=UTF-8"})
    public @ResponseBody Driver getDriver(@PathVariable long id){
        return  driverManager.get(id);
    }


    /**
     * Simple method for adding Drivers by consuming JSON request.
     * @param driver JSON document describing new Driver
     * @return JSON document describing added Driver (with appropriate ID field)
     */
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json; charset=UTF-8"},
            produces = {"application/json; charset=UTF-8"})
    public @ResponseBody Driver addDriver(@RequestBody Driver driver){

        LOGGER.info(String.format("Adding driver %s.", driver));
        return driverManager.add(driver);
    }

    /**
     * Simple method for updating Drivers by consuming JSON request.
     * @param driver JSON document describing Driver to update
     * @return JSON document describing Driver after updating
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = {"application/json; charset=UTF-8"},
            produces={"application/json; charset=UTF-8"})
    public @ResponseBody Driver updateDriver(@RequestBody Driver driver){

        LOGGER.info(String.format("Updating driver %s.", driver));
        return driverManager.update(driver);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteDriver(@RequestParam("id")long id){

        Driver driver = driverManager.get(id);
        LOGGER.info(String.format("Deleting driver %s.", driver));
        driverManager.delete(driver);
    }

}
