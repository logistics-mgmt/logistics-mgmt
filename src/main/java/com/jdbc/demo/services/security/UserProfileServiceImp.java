package com.jdbc.demo.services.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdbc.demo.UserProfileDAO;
import com.jdbc.demo.UserProfileService;
import com.jdbc.demo.domain.security.UserProfile;

@Service("userProfileService")
@Transactional
public class UserProfileServiceImp implements UserProfileService{
	 	@Autowired
	    UserProfileDAO dao;
	     
	    public UserProfile getById(int id) {
	        return dao.getById(id);
	    }
	 
	    public UserProfile getByType(String type){
	        return dao.getByType(type);
	    }
	 
	    public List<UserProfile> getAll() {
	        return dao.getAll();
	    }
}
