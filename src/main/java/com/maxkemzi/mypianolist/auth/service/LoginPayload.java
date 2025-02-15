package com.maxkemzi.mypianolist.auth.service;

public class LoginPayload {
	private String username;
	private String password;

	protected LoginPayload() {
	}

	public LoginPayload(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
