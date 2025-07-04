package com.maxkemzi.mypianolist.user.profile.service;

import org.springframework.web.multipart.MultipartFile;

public class UserProfileUpdatePayload {
	private String biography;
	private MultipartFile avatar;

	protected UserProfileUpdatePayload() {
	}

	public UserProfileUpdatePayload(String biography, MultipartFile avatar) {
		this.biography = biography;
		this.avatar = avatar;
	}

	public String getBiography() {
		return biography;
	}

	public MultipartFile getAvatar() {
		return avatar;
	}
}
