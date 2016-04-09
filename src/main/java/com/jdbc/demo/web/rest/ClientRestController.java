package com.jdbc.demo.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jdbc.demo.ClientDAO;
import com.jdbc.demo.domain.psql.Client;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ClientRestController.class);

	@Autowired
	ClientDAO clientManager;

	@RequestMapping(method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody List<Client> getClients() {
		return clientManager.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json; charset=UTF-8" })
	public @ResponseBody Client getClient(@PathVariable long id) {
		return clientManager.get(id);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json; charset=UTF-8" }, produces = {
			"application/json; charset=UTF-8" })
	public @ResponseBody Client addClient(@RequestBody Client client) {

		LOGGER.info(String.format("Adding client %s.", client));
		return clientManager.add(client);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, consumes = {
			"application/json; charset=UTF-8" }, produces = { "application/json; charset=UTF-8" })

	public @ResponseBody Client updateClient(@RequestBody Client client) {
		LOGGER.info(String.format("Updating client %s.", client));
		return clientManager.update(client);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteClient(@PathVariable("id") long id) {

		Client client = clientManager.get(id);
		LOGGER.info(String.format("Deleting client %s.", client));
		clientManager.delete(client);
	}

}
