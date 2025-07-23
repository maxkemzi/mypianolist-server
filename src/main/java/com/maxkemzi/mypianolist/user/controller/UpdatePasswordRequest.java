package com.maxkemzi.mypianolist.user.controller;

import jakarta.validation.constraints.NotBlank;

public class UpdatePasswordRequest {
	@NotBlank(message = "Password is required.")
	private final String password;

	public UpdatePasswordRequest(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
}
