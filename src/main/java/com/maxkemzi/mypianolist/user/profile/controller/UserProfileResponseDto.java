package com.maxkemzi.mypianolist.user.profile.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.maxkemzi.mypianolist.user.profile.model.UserProfile;

public class UserProfileResponseDto {
	private String username;
	private String biography;
	private String avatar;
	private LocalDateTime createdAt;

	protected UserProfileResponseDto() {
	}

	public UserProfileResponseDto(UserProfile up) {
		this.username = up.getUser().getUsername();
		this.biography = up.getBiography();

		String avatar = up.getAvatar();
		this.avatar = avatar != null && !avatar.isBlank() ? "/avatars/" + avatar : null;

		this.createdAt = up.getUser().getCreatedAt();
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

	public LocalDateTime getCreatedAt() {
		return createdAt.truncatedTo(ChronoUnit.MILLIS);
	}
}
