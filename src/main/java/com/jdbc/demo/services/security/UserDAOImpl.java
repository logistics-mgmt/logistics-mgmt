package com.jdbc.demo.services.security;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jdbc.demo.AbstractDAO;
import com.jdbc.demo.UserDAO;
import com.jdbc.demo.domain.security.User;
 
@Repository("userDAO")
public class UserDAOImpl extends AbstractDAO<Integer, User> implements UserDAO {
	 
    public User getById(int id) {
        User user = getByKey(id);
        if(user!=null){
            Hibernate.initialize(user.getUserRoles());
        }
        return user;
    }
 
    public User getByLogin(String login) {
        System.out.println("Login : "+login);
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("login", login));
        User user = (User)crit.uniqueResult();
        if(user!=null){
            Hibernate.initialize(user.getUserRoles());
        }
        return user;
    }
 
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        List<User> users = (List<User>) criteria.list();

        return users;
    }
 
    public void add(User user) {
        persist(user);
    }
 
    public void deleteByLogin(String login) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("login", login));
        User user = (User)crit.uniqueResult();
        delete(user);
    }
 
}