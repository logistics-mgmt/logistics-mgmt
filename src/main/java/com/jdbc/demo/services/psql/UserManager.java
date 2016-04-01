package com.jdbc.demo.services.psql;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jdbc.demo.domain.security.User;;

@Component
@Transactional
public class UserManager {
	private final static Logger LOGGER = LoggerFactory.getLogger(ClientManager.class);

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAll() {
		return sessionFactory.getCurrentSession().getNamedQuery("user.all").list();
	}
}
