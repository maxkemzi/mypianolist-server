package com.maxkemzi.mypianolist.user.controller;

import com.maxkemzi.mypianolist.user.model.User;

public class UserResponseDto {
	private String username;

	protected UserResponseDto() {
	}

	public UserResponseDto(User user) {
		this.username = user.getUsername();
	}

	public String getUsername() {
		return username;
	}
}
