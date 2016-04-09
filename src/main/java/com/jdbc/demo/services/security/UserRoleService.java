package com.jdbc.demo.services.security;

import java.util.List;

import com.jdbc.demo.domain.security.UserRole;

public interface UserRoleService {
	UserRole getById(int id);

	UserRole getByType(String type);

	List<UserRole> getAll();
}
