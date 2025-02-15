package com.maxkemzi.mypianolist.auth.service;

import com.maxkemzi.mypianolist.auth.service.jwt.JwtUser;
import com.maxkemzi.mypianolist.auth.service.jwt.JwtTokens;

public class LoginData {
	private final JwtUser user;
	private final JwtTokens tokens;

	public LoginData(JwtUser user, JwtTokens tokens) {
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
