package com.jdbc.demo.web.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.ClientDAO;

/**
 * Created by Bartosz on 23-Dec-15.
 */
@Controller
@RequestMapping("/clients")
public class ClientController {
	private final static Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
	@Autowired
	ClientDAO clientManager;

	@Autowired
	AddressDAO addressManager;

	@RequestMapping(method = RequestMethod.GET)
	public String getClients(ModelMap model) {
		model.addAttribute("clients", clientManager.getAll());
		return "clients";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addClient(ModelMap model) {
		model.addAttribute("addresses", addressManager.getAll());
		return "add_client";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editClient(@PathVariable long id, ModelMap model) {
		model.addAttribute("client", clientManager.get(id));
		model.addAttribute("addresses", addressManager.getAll());
		return "edit_client";
	}
}
