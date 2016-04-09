package com.jdbc.demo.services.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdbc.demo.UserRoleDAO;
import com.jdbc.demo.domain.security.UserRole;

@Service("userRoleService")
@Transactional
public class UserRoleServiceImp implements UserRoleService{
	 	@Autowired
	 	UserRoleDAO dao;
	     
	    public UserRole getById(int id) {
	        return dao.getById(id);
	    }
	 
	    public UserRole getByType(String type){
	        return dao.getByType(type);
	    }
	 
	    public List<UserRole> getAll() {
	        return dao.getAll();
	    }
}
