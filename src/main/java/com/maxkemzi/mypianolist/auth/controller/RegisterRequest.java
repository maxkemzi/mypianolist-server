package com.maxkemzi.mypianolist.auth.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
	@NotBlank(message = "Username is required.")
	private String username;

	@NotBlank(message = "Email is required.")
	@Email(message = "Email is invalid.")
	private String email;

	@NotBlank(message = "Password is required.")
	private String password;

	protected RegisterRequest() {
	}

	public RegisterRequest(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
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
}
