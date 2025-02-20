package com.maxkemzi.mypianolist.user.controller;

import jakarta.validation.constraints.NotBlank;

public class UpdateUsernameRequest {
	@NotBlank(message = "Username is required.")
	private final String username;

	public UpdateUsernameRequest(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}
