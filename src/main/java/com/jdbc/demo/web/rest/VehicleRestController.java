package com.jdbc.demo.web.rest;

import com.jdbc.demo.VehicleDAO;
import com.jdbc.demo.domain.psql.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Mateusz on 20-Dec-15.
 */
@RestController
@RequestMapping("/api/vehicles")
public class VehicleRestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(VehicleRestController.class);

    @Autowired
    VehicleDAO vehicleManager;

    @RequestMapping(method = RequestMethod.GET, produces={"application/json; charset=UTF-8"})
    public @ResponseBody
    List<Vehicle> getVehicles(){
        return  vehicleManager.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces={"application/json; charset=UTF-8"})
    public @ResponseBody Vehicle getVehicle(@PathVariable long id){
        return  vehicleManager.get(id);
    }


    /**
     * Simple method for adding Vehicles by consuming JSON request.
     * @param vehicle JSON document describing new Vehicle
     * @return JSON document describing added Vehicle (with appropriate ID field)
     */
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json; charset=UTF-8"},
            produces = {"application/json; charset=UTF-8"})
    public @ResponseBody Vehicle addVehicle(@RequestBody Vehicle vehicle){

        LOGGER.info(String.format("Adding vehicle %s.", vehicle));
        return vehicleManager.add(vehicle);
    }

    /**
     * Simple method for updating Vehicles by consuming JSON request.
     * @param vehicle JSON document describing Vehicle to update
     * @return JSON document describing Vehicle after updating
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = {"application/json; charset=UTF-8"},
            produces={"application/json; charset=UTF-8"})
    public @ResponseBody Vehicle updateVehicle(@RequestBody Vehicle vehicle){
        LOGGER.info(String.format("Updating vehicle %s.", vehicle));
        return vehicleManager.update(vehicle);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteVehicle(@PathVariable("id")long id){

        Vehicle vehicle = vehicleManager.get(id);
        LOGGER.info(String.format("Deleting vehicle %s.", vehicle));
        vehicleManager.delete(vehicle);
    }

}
