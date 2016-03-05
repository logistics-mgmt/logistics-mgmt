package com.jdbc.demo.persistence.psql;

import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.domain.psql.Driver;
import com.jdbc.demo.domain.psql.FreightTransport;
import com.jdbc.demo.domain.psql.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mateusz on 23-Oct-15.
 */

@Component
@Transactional(transactionManager = "txManager")
public class FreightTransportManager implements FreightTransportDAO {

    private final static Logger LOGGER = LoggerFactory.getLogger(FreightTransportManager.class);

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
    public List<FreightTransport> getAll() {
        return sessionFactory.getCurrentSession().getNamedQuery("freightTransport.all")
                .list();
    }

    @Override
    public void update(FreightTransport freightTransport) {
        sessionFactory.getCurrentSession().update(freightTransport);
    }

    @Override
    public FreightTransport get(long id) {
        return (FreightTransport) sessionFactory.getCurrentSession().get(FreightTransport.class, id);
    }

    @Override
    public FreightTransport add(FreightTransport freightTransport) {
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(freightTransport);
        return (FreightTransport) session.get(FreightTransport.class, id);
    }

    @Override
    public void delete(FreightTransport freightTransport) {
        sessionFactory.getCurrentSession().delete(freightTransport);
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        FreightTransport freightTransportToDelete = (FreightTransport) session.load(FreightTransport.class, id);
        session.delete(freightTransportToDelete);
        session.flush();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Boolean isDriverOnRoad(Driver driver){
        List<FreightTransport> activeTransports = sessionFactory.getCurrentSession()
                                                    .getNamedQuery("freightTransport.allActive")
                                                    .list();

        for(FreightTransport transport: activeTransports){
            if(transport.getDrivers().contains(driver))
                return true;
        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Boolean isVehicleOnRoad(Vehicle vehicle){
        List<FreightTransport> activeTransports = sessionFactory.getCurrentSession()
                .getNamedQuery("freightTransport.allActive")
                .list();

        for(FreightTransport transport: activeTransports){
            if(transport.getVehicles().contains(vehicle))
                return true;
        }

        return false;
    }
}
