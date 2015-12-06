package com.jdbc.demo.services;

import com.jdbc.demo.DriverDAO;
import com.jdbc.demo.domain.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mateusz on 22-Oct-15.
 */

@Component
@Transactional(transactionManager = "txManager")
public class DriverManager implements DriverDAO {

    private final static Logger LOGGER = LoggerFactory.getLogger(DriverManager.class);

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
    public List<Driver> getAll() {
        return sessionFactory.getCurrentSession().getNamedQuery("driver.all")
                .list();
    }

    @Override
    public void update(Driver driver) {
        sessionFactory.getCurrentSession().update(driver);
    }

    @Override
    public Driver get(long id) {
        return (Driver) sessionFactory.getCurrentSession().get(Driver.class, id);
    }

    @Override
    public Driver add(Driver driver) {
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(driver);
        return (Driver) session.get(Driver.class, id);
    }

    @Override
    public void delete(Driver driver) {
        sessionFactory.getCurrentSession().delete(driver);
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Driver driverToDelete = (Driver) session.load(Driver.class, id);
        session.delete(driverToDelete);
        session.flush();
    }
}
