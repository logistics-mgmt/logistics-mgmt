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


@Controller
@RequestMapping("/addresses")
public class AddressControler {
	private final static Logger LOGGER = LoggerFactory.getLogger(AddressControler.class);

	@Autowired
	AddressDAO addressManager;

	@RequestMapping(method = RequestMethod.GET)
	public String getAddresses(ModelMap model) {
		model.addAttribute("addresses", addressManager.getAll());
		return "addresses";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addAddress(ModelMap model) {
		return "add_address";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editAddress(@PathVariable long id, ModelMap model) {
		model.addAttribute("address", addressManager.get(id));
		return "edit_address";
	}
}