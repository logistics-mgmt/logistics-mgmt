package com.jdbc.demo.web;

import com.jdbc.demo.domain.Vehicle;
import com.jdbc.demo.services.VehicleEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by Mateusz on 14-Nov-15.
 */

@WebServlet(urlPatterns = "/vehicles")
public class VehicleServlet extends HttpServlet {

    private final static Logger LOGGER = LoggerFactory.getLogger(VehicleServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        boolean delete = (request.getParameter("delete") != null);
        boolean update = (request.getParameter("id") != null);

        Vehicle vehicle = new Vehicle();

        try {

            VehicleEntityManager vehicleEntityManager = new VehicleEntityManager();

            if (delete) {
                int id = Integer.parseInt(request.getParameter("id").trim());
                vehicleEntityManager.delete((long)id);
                LOGGER.info(String.format("Successfully deleted vehicle with ID: %d", id));
            } else {
                vehicle.setBrand(request.getParameter("brand"));
                vehicle.setEngine(Integer.parseInt(request.getParameter("engine").trim()));
                if (request.getParameter("horsepower") != null && request.getParameter("horsepower").length()>0)
                    vehicle.setHorsepower(Integer.parseInt(request.getParameter("horsepower").trim()));
                if (request.getParameter("mileage") != null && request.getParameter("mileage").length()>0)
                    vehicle.setMileage(Integer.parseInt(request.getParameter("mileage").trim()));
                vehicle.setModel(request.getParameter("type"));
                vehicle.setVIN(request.getParameter("VIN"));
                vehicle.setProductionDate(new Date(System.currentTimeMillis()));
                vehicle.setAvailable(Boolean.parseBoolean(request.getParameter("available")));
                if (update) {
                    vehicle.setId(Integer.parseInt(request.getParameter("id").trim()));
                    LOGGER.info("Updating vehicle:" + vehicle.toString());
                    vehicleEntityManager.update(vehicle);
                    LOGGER.info(String.format("Successfully updated vehicle: %s", vehicle));
                } else {
                    LOGGER.info("Adding vehicle:" + vehicle.toString());
                    vehicleEntityManager.add(vehicle);
                    LOGGER.info(String.format("Successfully added vehicle: %s", vehicle));
                }
            }
        } catch (Exception e) {
            LOGGER.error(String.format("Failed to handle request: %s", request));
            if (delete) {
                LOGGER.error(String.format("Exception thrown while deleting Vehicle: %s", vehicle), e);
                response.sendError(500, String.format("Internal Server Error: Deleting Vehicle %s failed. \n", vehicle));
            } else if (update) {
                LOGGER.error(String.format("Exception thrown while updating Vehicle: %s", vehicle), e);
                response.sendError(500, String.format("Internal Server Error: Updating Vehicle %s failed. \n", vehicle));
            } else {
                LOGGER.error(String.format("Exception thrown while adding Vehicle: %s", vehicle), e);
                response.sendError(500, String.format("Internal Server Error: Adding Vehicle %s failed. \n", vehicle));
            }
            vehicle = null;
        }

        response.setStatus(200);
        LOGGER.info(response.toString());

        //Redirect to vehicles.jsp
        request.getRequestDispatcher("/vehicles.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        boolean getAll = (request.getParameter("id") == null);

        try {
            VehicleEntityManager vehicleEntityManager = new VehicleEntityManager();
            if (getAll) {
                for (Vehicle vehicle : vehicleEntityManager.getAll())
                    response.getWriter().write(vehicle.toString() + '\n');

                LOGGER.info("Successfully fetched Vehicles list.");
            } else {
                int id = Integer.parseInt(request.getParameter("id").trim());
                Vehicle vehicle = vehicleEntityManager.get(id);
                response.getWriter().write(vehicle.toString());
                LOGGER.info(String.format("Successfully fetched Vehicle with id: %d.", id));
            }

        } catch (Exception e) {
            LOGGER.error(String.format("Failed to handle request: %s", request));
            if (getAll) {
                LOGGER.error("Exception thrown while getting Vehicles list: %s", e);
                response.sendError(500, "Internal Server Error: Getting Vehicles list failed. \n");
            } else {
                LOGGER.error(String.format("Exception thrown while getting Vehicle with ID: %s", request.getParameter("id")), e);
                response.sendError(500, String.format("Internal Server Error: Fetching Vehicle with id: %s failed. \n", request.getParameter("id")));
            }
        }

        response.setStatus(200);


    }
}
