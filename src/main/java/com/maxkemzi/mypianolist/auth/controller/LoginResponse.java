package com.maxkemzi.mypianolist.auth.controller;

import com.maxkemzi.mypianolist.auth.service.jwt.JwtUser;

public class LoginResponse {
	private JwtUserResponseDto user;
	private String accessToken;

	public LoginResponse(JwtUser user, String accessToken) {
		this.user = new JwtUserResponseDto(user);
		this.accessToken = accessToken;
	}

	public JwtUserResponseDto getUser() {
		return user;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
