package com.maxkemzi.mypianolist.user.profile.controller;

import java.time.LocalDateTime;

import com.maxkemzi.mypianolist.user.profile.service.UserProfile;
import com.maxkemzi.mypianolist.user.profile.service.UserProfileStats;

public class UserProfileResponseDto {
	private String username;
	private String avatar;
	private LocalDateTime joinedAt;
	private UserProfileStats stats;

	protected UserProfileResponseDto() {
	}

	public UserProfileResponseDto(UserProfile up) {
		if (up.getUser() != null) {
			this.username = up.getUser().getUsername();
			this.avatar = up.getUser().getAvatar();
			this.joinedAt = up.getUser().getCreatedAt();
		}
		this.stats = up.getStats();
	}

	public String getUsername() {
		return username;
	}

	public String getAvatar() {
		return avatar;
	}

	public LocalDateTime getJoinedAt() {
		return joinedAt;
	}

	public UserProfileStats getStats() {
		return stats;
	}

}
