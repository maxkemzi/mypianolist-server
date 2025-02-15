package com.maxkemzi.mypianolist.auth.controller;

import com.maxkemzi.mypianolist.auth.service.jwt.JwtUser;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtTokens;

public class LoginResponse {
	private JwtUser user;
	private JwtTokens tokens;

	public LoginResponse(JwtUser user, JwtTokens tokens) {
		this.user = user;
		this.tokens = tokens;
	}

	public JwtUser getUser() {
		return user;
	}

	public JwtTokens getTokens() {
		return tokens;
	}
}
