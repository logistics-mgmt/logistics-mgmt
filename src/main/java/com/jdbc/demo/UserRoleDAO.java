package com.jdbc.demo;

import java.util.List;

import com.jdbc.demo.domain.security.UserRole;

public interface UserRoleDAO {
	public List<UserRole> getAll();

	public UserRole getByType(String type);

	public UserRole getById(int id);
}
