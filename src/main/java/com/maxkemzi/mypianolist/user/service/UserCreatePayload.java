package com.maxkemzi.mypianolist.user.service;

import com.maxkemzi.mypianolist.user.model.UserRole;

public class UserCreatePayload {
	private String username;
	private String email;
	private String password;
	private UserRole role;

	public UserCreatePayload(String username, String email, String password, UserRole role) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public UserRole getRole() {
		return role;
	}
}
