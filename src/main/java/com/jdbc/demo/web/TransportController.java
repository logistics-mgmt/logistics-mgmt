package com.jdbc.demo.web;

import com.jdbc.demo.FreightTransportDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * Created by Bartosz on 23-Dec-15.
 */
@Controller
@RequestMapping("/transports")
public class TransportController {
	private final static Logger LOGGER = LoggerFactory.getLogger(TransportController.class);
	@Autowired
	FreightTransportDAO transportManager;
	
	@RequestMapping(method = RequestMethod.GET )
    public String getTransports(ModelMap model){
        model.addAttribute("transports", transportManager.getAll());
        return "transports";
    }
}
