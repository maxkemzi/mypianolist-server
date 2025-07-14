package com.maxkemzi.mypianolist.auth.controller;

import com.maxkemzi.mypianolist.auth.service.jwt.JwtUser;

public class JwtUserResponseDto {
	private final String username;
	private final String avatar;

	public JwtUserResponseDto(JwtUser user) {
		this.username = user.getUsername();

		String avatar = user.getAvatar();
		this.avatar = avatar != null && !avatar.isBlank() ? "/avatars/" + avatar : null;
	}

	public String getUsername() {
		return username;
	}

	public String getAvatar() {
		return avatar;
	}
}
