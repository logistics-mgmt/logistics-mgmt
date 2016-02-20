package com.jdbc.demo.web.mvc;

import com.jdbc.demo.ClientDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * Created by Bartosz on 23-Dec-15.
 */
@Controller
@RequestMapping("/clients")
public class ClientController {
	private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
	@Autowired
	ClientDAO clientManager;
	
	@RequestMapping(method = RequestMethod.GET )
    public String getClients(ModelMap model){
        model.addAttribute("clients", clientManager.getAll());
        return "clients";
    }
}
