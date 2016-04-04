package com.jdbc.demo.services.security;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jdbc.demo.AbstractDAO;
import com.jdbc.demo.UserProfileDAO;
import com.jdbc.demo.domain.security.UserProfile;
@Repository("userProfileDAO")
public class UserProfileDAOImpl extends AbstractDAO<Integer, UserProfile>implements UserProfileDAO{
	 
    public UserProfile getById(int id) {
        return getByKey(id);
    }
 
    public UserProfile getByType(String type) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("type", type));
        return (UserProfile) crit.uniqueResult();
    }
     
    @SuppressWarnings("unchecked")
    public List<UserProfile> getAll(){
        Criteria crit = createEntityCriteria();
        crit.addOrder(Order.asc("type"));
        return (List<UserProfile>)crit.list();
    }
     
}
