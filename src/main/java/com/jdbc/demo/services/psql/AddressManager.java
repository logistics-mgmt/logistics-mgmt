package com.jdbc.demo.services.psql;

import com.jdbc.demo.AddressDAO;
import com.jdbc.demo.domain.psql.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mateusz on 31-Oct-15.
 */

@Component
@Transactional(transactionManager = "txManager")
public class AddressManager implements AddressDAO {

	private final static Logger LOGGER = LoggerFactory.getLogger(AddressManager.class);

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
	public List<Address> getAll() {
		return sessionFactory.getCurrentSession().getNamedQuery("address.all").list();
	}

	@Override
	public Address update(Address address) {
		sessionFactory.getCurrentSession().update(address);
		return (Address) sessionFactory.getCurrentSession().get(Address.class, address.getId());
	}

	@Override
	public Address get(long id) {
		return (Address) sessionFactory.getCurrentSession().get(Address.class, id);
	}

	@Override
	public Address add(Address address) {
		Session session = sessionFactory.getCurrentSession();
		Long id = (Long) session.save(address);
		return (Address) session.get(Address.class, id);
	}

	@Override
	public void delete(Address address) {
		sessionFactory.getCurrentSession().delete(address);
	}

	@Override
	public void delete(long id) {
		Session session = sessionFactory.getCurrentSession();
		Address addressToDelete = (Address) session.load(Address.class, id);
		session.delete(addressToDelete);
		session.flush();
	}
}
