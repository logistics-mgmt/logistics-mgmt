package com.jdbc.demo.domain.security;

public enum UserRoleType {
	USER("USER"), FORWARDER("FORWARDER"), ADMIN("ADMIN");

	String userRoleType;

	private UserRoleType(String userRoleType) {
		this.userRoleType = userRoleType;
	}

	public String getUserRoleType() {
		return userRoleType;
	}

}