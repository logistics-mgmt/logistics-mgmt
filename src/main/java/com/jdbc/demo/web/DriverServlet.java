package com.jdbc.demo.web;

import com.jdbc.demo.domain.Driver;
import com.jdbc.demo.services.AddressEntityManager;
import com.jdbc.demo.services.DriverEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(urlPatterns = "/driver")
public class DriverServlet extends HttpServlet {

    private final static Logger LOGGER = LoggerFactory.getLogger(DriverServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        boolean delete = (request.getParameter("delete") != null);
        boolean update = (request.getParameter("id") != null);

        Driver driver = new Driver();

        try {

            DriverEntityManager driverEntityManager = new DriverEntityManager();
            AddressEntityManager addressEntityManager = new AddressEntityManager();

            if (delete) {
                int id = Integer.parseInt(request.getParameter("id").trim());
                LOGGER.info("Deleting driver:" + driverEntityManager.get(id));
                driverEntityManager.delete(id);
                LOGGER.info(String.format("Successfully deleted driver with ID: %d", id));
            }
            else{
                driver.setFirstName(request.getParameter("first-name"));
                driver.setLastName(request.getParameter("last-name"));
                driver.setPESEL(request.getParameter("pesel"));
                driver.setAvailable(Boolean.parseBoolean(request.getParameter("available")));
                if (request.getParameter("salary") != null && request.getParameter("salary").length()>0)
                    driver.setSalary(new BigDecimal(request.getParameter("salary")).setScale(2, BigDecimal.ROUND_CEILING));
                if (request.getParameter("salary-bonus") != null && request.getParameter("salary-bonus").length()>0)
                    driver.setSalaryBonus(new BigDecimal(request.getParameter("salary-bonus")).setScale(2, BigDecimal.ROUND_CEILING));
                driver.setDeleted(false);
                driver.setAddress(addressEntityManager.get(Integer.parseInt(request.getParameter("address-id").trim())));
                if (update){
                    driver.setId(Integer.parseInt(request.getParameter("id").trim()));
                    LOGGER.info("Updating driver:" + driver.toString());
                    driverEntityManager.update(driver);
                    LOGGER.info(String.format("Successfully updated driver: %s", driver));
                }else {
                    LOGGER.info("Adding driver:" + driver.toString());
                    driverEntityManager.add(driver);
                    LOGGER.info(String.format("Successfully added driver: %s", driver));
                }
            }
        } catch (Exception e) {
            LOGGER.error(String.format("Failed to handle request: %s", request));
            if (delete) {
                LOGGER.error(String.format("Exception thrown while deleting Driver: %s", driver), e);
                response.sendError(500, String.format("Internal Server Error: Deleting Driver %s failed. \n", driver));
            } else if (update) {
                LOGGER.error(String.format("Exception thrown while updating Driver: %s", driver), e);
                response.sendError(500, String.format("Internal Server Error: Updating Driver %s failed. \n", driver));
            } else {
                LOGGER.error(String.format("Exception thrown while adding Driver: %s", driver), e);
                response.sendError(500, String.format("Internal Server Error: Adding Driver %s failed. \n", driver));
            }
            driver = null;
        }

        response.setStatus(200);
        LOGGER.info(response.toString());

        //Redirect to drivers.jsp
        request.getRequestDispatcher("/drivers.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        boolean getAll = (request.getParameter("id") == null);

        try {
            DriverEntityManager driverEntityManager = (DriverEntityManager) getServletContext().getAttribute("drivers");
            if (getAll) {
                for (Driver driver : driverEntityManager.getAll())
                    response.getWriter().write(driver.toString() + '\n');
                LOGGER.info("Successfully fetched Drivers list.");
            } else {
                int id = Integer.parseInt(request.getParameter("id"));
                Driver driver = driverEntityManager.get(id);
                response.getWriter().write(driver.toString());
                LOGGER.info(String.format("Successfully fetched Driver with id: %d.", id));
            }

        } catch (Exception e) {
            LOGGER.error(String.format("Failed to handle request: %s", request));
            if (getAll) {
                LOGGER.error("Exception thrown while getting Drivers list: %s", e);
                response.sendError(500, "Internal Server Error: Getting Drivers list failed. \n");
            } else {
                LOGGER.error(String.format("Exception thrown while getting Driver with ID: %s", request.getParameter("id")), e);
                response.sendError(500, String.format("Internal Server Error: Fetching Driver with id: %s failed. \n", request.getParameter("id")));
            }
        }

        response.setStatus(200);


    }

}
