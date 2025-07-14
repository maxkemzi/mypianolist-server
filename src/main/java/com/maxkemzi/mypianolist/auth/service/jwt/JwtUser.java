package com.maxkemzi.mypianolist.auth.service.jwt;

public class JwtUser {
	private final String username;
	private final String avatar;

	public JwtUser(String username, String avatar) {
		this.username = username;
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public String getAvatar() {
		return avatar;
	}
}
