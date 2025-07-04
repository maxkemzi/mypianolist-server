package com.maxkemzi.mypianolist.user.profile.service;

import com.maxkemzi.mypianolist.user.model.User;

public class UserProfileCreatePayload {
	private User user;
	private String biography;
	private String avatar;

	protected UserProfileCreatePayload() {
	}

	public UserProfileCreatePayload(User user, String biography, String avatar) {
		this.user = user;
		this.biography = biography;
		this.avatar = avatar;
	}

	public User getUser() {
		return user;
	}

	public String getBiography() {
		return biography;
	}

	public String getAvatar() {
		return avatar;
	}
}
