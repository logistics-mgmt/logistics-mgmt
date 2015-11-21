package com.jdbc.demo.services;

import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.VehicleDAO;
import com.jdbc.demo.domain.FreightTransport;
import com.jdbc.demo.domain.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mciesielski on 2015-10-23.
 */

@Component
@Transactional
public class VehicleEntityManager implements VehicleDAO {

    private final static Logger LOGGER = LoggerFactory.getLogger(VehicleEntityManager.class);

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
    public List<Vehicle> getAll() {
        return sessionFactory.getCurrentSession().getNamedQuery("vehicle.all")
                .list();
    }

    @Override
    public void update(Vehicle vehicle) {
        sessionFactory.getCurrentSession().update(vehicle);
    }

    @Override
    public Vehicle get(long id) {
        return (Vehicle) sessionFactory.getCurrentSession().get(Vehicle.class, id);
    }

    @Override
    public Vehicle add(Vehicle vehicle) {
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(vehicle);
        return (Vehicle) session.get(Vehicle.class, id);
    }

    @Override
    public void delete(Vehicle vehicle) {
        sessionFactory.getCurrentSession().delete(vehicle);
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Vehicle vehicleToDelete = (Vehicle) session.load(Vehicle.class, id);
        session.delete(vehicleToDelete);
        session.flush();
    }
}
