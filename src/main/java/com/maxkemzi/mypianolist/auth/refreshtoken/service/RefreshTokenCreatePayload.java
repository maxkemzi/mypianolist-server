package com.maxkemzi.mypianolist.auth.refreshtoken.service;

public class RefreshTokenCreatePayload {
	private String token;
	private String username;

	public RefreshTokenCreatePayload(String token, String username) {
		this.token = token;
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public String getUsername() {
		return username;
	}
}
