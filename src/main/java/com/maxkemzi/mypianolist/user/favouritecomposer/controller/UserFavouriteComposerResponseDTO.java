package com.maxkemzi.mypianolist.user.favouritecomposer.controller;

import java.util.UUID;

import com.maxkemzi.mypianolist.composer.controller.ComposerResponseDTO;
import com.maxkemzi.mypianolist.user.controller.UserResponseDTO;
import com.maxkemzi.mypianolist.user.favouritecomposer.model.UserFavouriteComposer;

public class UserFavouriteComposerResponseDTO {
	private UUID id;
	private UserResponseDTO user;
	private ComposerResponseDTO composer;

	protected UserFavouriteComposerResponseDTO() {
	}

	public UserFavouriteComposerResponseDTO(UserFavouriteComposer ufc) {
		this.id = ufc.getId();
		this.user = new UserResponseDTO(ufc.getUser());
		this.composer = new ComposerResponseDTO(ufc.getComposer());
	}

	public UUID getId() {
		return id;
	}

	public UserResponseDTO getUser() {
		return user;
	}

	public ComposerResponseDTO getComposer() {
		return composer;
	}
}
