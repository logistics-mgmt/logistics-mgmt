package com.jdbc.demo.services.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdbc.demo.UserDAO;
import com.jdbc.demo.domain.security.User;
 
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
 
	 @Autowired
	    private UserDAO dao;
	 
	    public User getById(int id) {
	        return dao.getById(id);
	    }
	 
	    public User getByLogin(String login) {
	        User user = dao.getByLogin(login);
	        return user;
	    }
	 
	    public void addUser(User user) {
	        dao.add(user);
	    }
	 

	    public void updateUser(User user) {
	        User entity = dao.getById(user.getId());
	        if(entity!=null){
	            entity.setLogin(user.getLogin());
	            entity.setPassword(user.getPassword());
	            entity.setFirstName(user.getFirstName());
	            entity.setLastName(user.getLastName());
	            entity.setUserRoles(user.getUserRoles());
	        }
	    }
	 
	     
	    public void deleteUserByLogin(String login) {
	        dao.deleteByLogin(login);
	    }
	 
	    public List<User> getAll() {
	        return dao.getAll();
	    }
	 
	    public boolean isUserLoginUnique(Integer id, String login) {
	        User user = getByLogin(login);
	        return ( user == null || ((id != null) && (user.getId() == id)));
	    }
 
}