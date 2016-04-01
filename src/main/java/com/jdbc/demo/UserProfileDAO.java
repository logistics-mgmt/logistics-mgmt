package com.jdbc.demo;

import com.jdbc.demo.domain.security.UserProfile;

import java.util.List;

public interface UserProfileDAO {
	public List<UserProfile> getAll();

	public UserProfile getByType(String type);

	public UserProfile getById(int id);
}
