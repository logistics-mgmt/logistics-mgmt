package com.jdbc.demo.services.security;

import java.util.List;

import com.jdbc.demo.domain.security.User;

public interface UserService {
	 
	    User getById(int id);
	     
	    User getByLogin(String login);
	     
	    void addUser(User user);
	     
	    void updateUser(User user);
	     
	    void deleteUserByLogin(String login);
	 
	    List<User> getAll(); 
	     
	    boolean isUserLoginUnique(Integer id, String login);
	 
     
}