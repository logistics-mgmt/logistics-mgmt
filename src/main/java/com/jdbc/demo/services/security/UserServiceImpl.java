package com.jdbc.demo.services.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.jdbc.demo.UserDAO;
import com.jdbc.demo.UserService;
import com.jdbc.demo.domain.security.*;
 
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
 
	 @Autowired
	    private UserDAO dao;
	 
	    public User getById(int id) {
	        return dao.getById(id);
	    }
	 
	    public User getBySSO(String sso) {
	        User user = dao.getBySSO(sso);
	        return user;
	    }
	 
	    public void addUser(User user) {
	        dao.add(user);
	    }
	 

	    public void updateUser(User user) {
	        User entity = dao.getById(user.getId());
	        if(entity!=null){
	            entity.setSsoId(user.getSsoId());
	            entity.setPassword(user.getPassword());
	            entity.setFirstName(user.getFirstName());
	            entity.setLastName(user.getLastName());
	            entity.setUserProfiles(user.getUserProfiles());
	        }
	    }
	 
	     
	    public void deleteUserBySSO(String sso) {
	        dao.deleteBySSO(sso);
	    }
	 
	    public List<User> getAllUsers() {
	        return dao.getAllUsers();
	    }
	 
	    public boolean isUserSSOUnique(Integer id, String sso) {
	        User user = getBySSO(sso);
	        return ( user == null || ((id != null) && (user.getId() == id)));
	    }
 
}