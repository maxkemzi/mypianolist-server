package com.maxkemzi.mypianolist.auth.controller;

import com.maxkemzi.mypianolist.auth.service.jwt.JwtUser;

public class LoginResponse {
	private JwtUser user;
	private String accessToken;

	public LoginResponse(JwtUser user, String accessToken) {
		this.user = user;
		this.accessToken = accessToken;
	}

	public JwtUser getUser() {
		return user;
	}

	public String getAccessToken() {
		return accessToken;
	}
}
