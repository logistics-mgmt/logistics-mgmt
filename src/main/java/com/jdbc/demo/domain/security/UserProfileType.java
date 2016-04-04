package com.jdbc.demo.domain.security;

public enum UserProfileType {
	USER("USER"), FORWARDER("FORWARDER"), ADMIN("ADMIN");

	String userProfileType;

	private UserProfileType(String userProfileType) {
		this.userProfileType = userProfileType;
	}

	public String getUserProfileType() {
		return userProfileType;
	}

}