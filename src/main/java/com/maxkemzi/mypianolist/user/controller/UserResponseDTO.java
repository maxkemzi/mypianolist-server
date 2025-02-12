package com.maxkemzi.mypianolist.user.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import com.maxkemzi.mypianolist.user.model.User;

public class UserResponseDTO {
	private UUID id;
	private String username;
	private String avatar;
	private LocalDateTime createdAt;

	protected UserResponseDTO() {
	}

	public UserResponseDTO(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.avatar = user.getAvatar();
		this.createdAt = user.getCreatedAt();
	}

	public UUID getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getAvatar() {
		return avatar;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
