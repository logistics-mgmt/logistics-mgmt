package com.jdbc.demo.services;

import com.jdbc.demo.FreightTransportDAO;
import com.jdbc.demo.domain.FreightTransport;
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
@Transactional
public class FreightTransportEntityManager implements FreightTransportDAO {

    private final static Logger LOGGER = LoggerFactory.getLogger(FreightTransportEntityManager.class);

    @Autowired
    private SessionFactory sessionFactory;

    private AddressEntityManager addressEntityManager;

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
}
