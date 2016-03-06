package com.jdbc.demo.services.psql;

import com.jdbc.demo.ClientDAO;
import com.jdbc.demo.domain.psql.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mateusz on 02-Nov-15.
 */
@Component
@Transactional(transactionManager = "txManager")
public class ClientManager implements ClientDAO {

	private final static Logger LOGGER = LoggerFactory.getLogger(ClientManager.class);

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Client> getAll() {
		return sessionFactory.getCurrentSession().getNamedQuery("client.all").list();
	}

	@Override
	public Client update(Client client) {
		sessionFactory.getCurrentSession().update(client);
		return (Client) sessionFactory.getCurrentSession().get(Client.class, client.getId());
	}

	@Override
	public Client get(long id) {
		return (Client) sessionFactory.getCurrentSession().get(Client.class, id);
	}

	@Override
	public Client add(Client client) {
		Session session = sessionFactory.getCurrentSession();
		Long id = (Long) session.save(client);
		return (Client) session.get(Client.class, id);
	}

	@Override
	public void delete(Client client) {
		sessionFactory.getCurrentSession().delete(client);
	}

	@Override
	public void delete(long id) {
		Session session = sessionFactory.getCurrentSession();
		Client clientToDelete = (Client) session.load(Client.class, id);
		session.delete(clientToDelete);
		session.flush();
	}
}
