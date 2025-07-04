package com.maxkemzi.mypianolist.user.profile.controller;

public class UserProfileUpdateRequest {
	private String biography;

	protected UserProfileUpdateRequest() {
	}

	public UserProfileUpdateRequest(String biography) {
		this.biography = biography;
	}

	public String getBiography() {
		return biography;
	}
}
