package com.maxkemzi.mypianolist.user.profile.service;

import com.maxkemzi.mypianolist.user.model.User;

public class UserProfile {
	private User user;
	private UserProfileStats stats;

	protected UserProfile() {
	}

	public UserProfile(User u, UserProfileStats s) {
		this.user = u;
		this.stats = s;
	}

	public User getUser() {
		return user;
	}

	public UserProfileStats getStats() {
		return stats;
	}
}
