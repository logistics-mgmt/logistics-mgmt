package com.jdbc.demo.web;

import com.jdbc.demo.VehicleDAO;
import com.jdbc.demo.domain.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

/**
 * Created by Mateusz on 06-Dec-15.
 */
@Controller
@RequestMapping("/vehicles")
public class VehicleController {


        private final static Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

        @Autowired
        VehicleDAO vehicleManager;

        @RequestMapping(method = RequestMethod.GET, params = {"!id"} )
        public String getVehicles(ModelMap model){
            model.addAttribute("vehicles", vehicleManager.getAll());
            return "vehicles";
        }

        //TODO: Use ModelAttribute instead of params list for form requests handling.
        @RequestMapping(method = RequestMethod.POST, params = {"!id"} )
        public String addVehicle(@RequestParam("brand")String brand, @RequestParam("type")String type,
                                @RequestParam(value = "horsepower", required = false)Integer horsepower,
                                 @RequestParam(value = "mileage", required = false)Integer mileage,
                                @RequestParam("VIN")String VIN, @RequestParam("available")Boolean available,  ModelMap model){

            Vehicle vehicle = new Vehicle();
            vehicle.setBrand(brand);
            vehicle.setModel(type);
            if(horsepower != null)
                vehicle.setHorsepower(horsepower);
            vehicle.setAvailable(available);
            if(mileage != null)
                vehicle.setMileage(mileage);
            vehicle.setVIN(VIN);
            //TODO: get rid of this workaround by changing database creation script, or handling production date param.
            vehicle.setProductionDate(new Date(System.currentTimeMillis()));
            LOGGER.info(String.format("Adding vehicle %s.", vehicle));
            vehicleManager.add(vehicle);

            model.addAttribute("vehicles", vehicleManager.getAll());
            return "vehicles";
        }

        //TODO: Use ModelAttribute instead of params list for form requests handling.
        @RequestMapping(method = RequestMethod.POST, params = {"id"} )
        public String updateVehicle(@RequestParam("id")long id,@RequestParam("brand")String brand, @RequestParam("type")String type,
                                    @RequestParam("horsepower")int horsepower, @RequestParam("mileage")int mileage,
                                    @RequestParam("VIN")String VIN, @RequestParam(value = "available", required = false)Boolean available,
                                    ModelMap model){

            Vehicle vehicle = vehicleManager.get(id);
            vehicle.setBrand(brand);
            vehicle.setModel(type);
            vehicle.setHorsepower(horsepower);
            if(available != null)
                vehicle.setAvailable(available);
            vehicle.setMileage(mileage);
            vehicle.setVIN(VIN);
            LOGGER.info(String.format("Updating vehicle %s.", vehicle));
            vehicleManager.update(vehicle);

            model.addAttribute("vehicles", vehicleManager.getAll());
            return "vehicles";
        }

        //TODO: Use ModelAttribute instead of params list for form requests handling.
        @RequestMapping(method = RequestMethod.POST, params = {"id", "delete"} )
        public String deleteVehicle(@RequestParam("id")long id, ModelMap model){

            Vehicle vehicle = vehicleManager.get(id);
            LOGGER.info(String.format("Deleting vehicle %s.", vehicle));
            vehicleManager.delete(vehicle);

            model.addAttribute("vehicles", vehicleManager.getAll());
            return "vehicles";
        }

    //Simple example of producing JSON responses
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces={"application/json; charset=UTF-8"})
    public @ResponseBody
    Vehicle getVehicle(@PathVariable long id, ModelMap model){
        return  vehicleManager.get(id);
    }
}
