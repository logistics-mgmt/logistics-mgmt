package com.jdbc.demo;

import java.util.List;

import com.jdbc.demo.domain.security.UserProfile;

public interface UserProfileService {
	UserProfile getById(int id);

	UserProfile getByType(String type);

	List<UserProfile> getAll();
}
