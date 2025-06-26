package com.maxkemzi.mypianolist.auth.service.jwt;

public class JwtUser {
	private final String username;
	private final String biography;
	private final String avatar;

	public JwtUser(String username, String biography, String avatar) {
		this.username = username;
		this.biography = biography;
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public String getBiography() {
		return biography;
	}

	public String getAvatar() {
		return avatar;
	}
}
