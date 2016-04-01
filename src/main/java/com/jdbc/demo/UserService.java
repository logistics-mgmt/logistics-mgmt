package com.jdbc.demo;

import java.util.List;

import com.jdbc.demo.domain.security.*;

public interface UserService {
	 
	    User getById(int id);
	     
	    User getBySSO(String sso);
	     
	    void addUser(User user);
	     
	    void updateUser(User user);
	     
	    void deleteUserBySSO(String sso);
	 
	    List<User> getAllUsers(); 
	     
	    boolean isUserSSOUnique(Integer id, String sso);
	 
     
}