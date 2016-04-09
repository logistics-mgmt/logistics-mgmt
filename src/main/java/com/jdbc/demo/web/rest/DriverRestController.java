package com.jdbc.demo.web.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.domain.psql.Driver;
import com.jdbc.demo.domain.schedule.ScheduleEvent;

/**
 * Created by Mateusz on 12-Dec-15.
 */

@RestController
@RequestMapping("/api/drivers")
public class DriverRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(DriverRestController.class);

    @Autowired
    DriverDAO driverManager;

    /**
     * Allows binding of dates provided by FullCalendar or other clients which use 'yyyy-MM-dd' date format.
     * @param binder
     */
    @InitBinder
    private void dateBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(method = RequestMethod.GET, produces={"application/json; charset=UTF-8"})
    public @ResponseBody
    List<Driver> getDrivers(){
        return  driverManager.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces={"application/json; charset=UTF-8"})
    public @ResponseBody Driver getDriver(@PathVariable long id){
        Driver driver = driverManager.get(id);
        LOGGER.info(String.format("GET driver: %s.", driver));
        return  driver;
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
    public void deleteDriver(@PathVariable("id")long id){

        Driver driver = driverManager.get(id);
        LOGGER.info(String.format("Deleting driver %s.", driver));
        driverManager.delete(driver);
    }

    @RequestMapping(value = "/{id}/schedule", method = RequestMethod.GET)
    public List<ScheduleEvent> getSchedule(@PathVariable("id")long id){

        Driver driver = driverManager.get(id);
        LOGGER.info(String.format("GET schedule for driver %s.", driver));
        return driver.getSchedule();
    }

    @RequestMapping(value = "/{id}/schedule", params = {"start", "end"}, method = RequestMethod.GET)
    public List<ScheduleEvent> getSchedule(@PathVariable("id")long id,@RequestParam("start") Date start,
                                           @RequestParam("end") Date end){

        Driver driver = driverManager.get(id);
        LOGGER.info(String.format("GET schedule(%s-%s) for driver %s.", start, end, driver));
        return driver.getSchedule(start, end);
    }

}
