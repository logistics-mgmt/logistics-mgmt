package com.jdbc.demo.web.rest;

import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.domain.psql.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Mateusz on 15-Dec-15.
 */

@RestController
@RequestMapping("/api/addresses")
public class AddressRestController {

	private final static Logger LOGGER = LoggerFactory.getLogger(AddressRestController.class);

	@Autowired
	AddressDAO addressManager;

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody List<Address> getAddresses() {
		return addressManager.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody Address getAddress(@PathVariable long id) {
		return addressManager.get(id);
	}

	/**
	 * Simple method for adding Addresss by consuming JSON request.
	 * 
	 * @param address
	 *            JSON document describing new Address
	 * @return JSON document describing added Address (with appropriate ID
	 *         field)
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json; charset=UTF-8" }, produces = {
			"application/json; charset=UTF-8" })
	public @ResponseBody Address addAddress(@RequestBody Address address) {

		LOGGER.info(String.format("Adding address %s.", address));
		return addressManager.add(address);
	}

	/**
	 * Simple method for updating Addresss by consuming JSON request.
	 * 
	 * @param address
	 *            JSON document describing Address to update
	 * @return JSON document describing Address after updating
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = {
			"application/json; charset=UTF-8" }, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody Address updateAddress(@RequestBody Address address) {

		LOGGER.info(String.format("Updating address %s.", address));
		return addressManager.update(address);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteAddress(@PathVariable("id") long id) {

		Address address = addressManager.get(id);
		LOGGER.info(String.format("Deleting address %s.", address));
		addressManager.delete(address);
	}
}
