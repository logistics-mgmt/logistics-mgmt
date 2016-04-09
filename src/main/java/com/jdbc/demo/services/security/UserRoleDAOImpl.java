package com.jdbc.demo.services.security;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jdbc.demo.AbstractDAO;
import com.jdbc.demo.UserRoleDAO;
import com.jdbc.demo.domain.security.UserRole;
@Repository("userRoleDAO")
public class UserRoleDAOImpl extends AbstractDAO<Integer, UserRole>implements UserRoleDAO{
	 
    public UserRole getById(int id) {
        return getByKey(id);
    }
 
    public UserRole getByType(String type) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("type", type));
        return (UserRole) crit.uniqueResult();
    }
     
    @SuppressWarnings("unchecked")
    public List<UserRole> getAll(){
        Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("type"));
        return (List<UserRole>)crit.list();
    }
     
}
