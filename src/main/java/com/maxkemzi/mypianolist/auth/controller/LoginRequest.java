package com.maxkemzi.mypianolist.auth.controller;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank(message = "Username is required.")
	private String username;

	@NotBlank(message = "Email is required.")
	private String password;

	protected LoginRequest() {
	}

	public LoginRequest(String username, String password) {
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
