package com.maxkemzi.mypianolist.user.favouritecomposer.controller;

import java.util.UUID;

import com.maxkemzi.mypianolist.composer.controller.ComposerResponseDto;
import com.maxkemzi.mypianolist.user.controller.UserResponseDto;
import com.maxkemzi.mypianolist.user.favouritecomposer.model.UserFavouriteComposer;

public class UserFavouriteComposerResponseDto {
	private UUID id;
	private UserResponseDto user;
	private ComposerResponseDto composer;

	protected UserFavouriteComposerResponseDto() {
	}

	public UserFavouriteComposerResponseDto(UserFavouriteComposer ufc) {
		this.id = ufc.getId();
		this.user = new UserResponseDto(ufc.getUser());
		this.composer = new ComposerResponseDto(ufc.getComposer());
	}

	public UUID getId() {
		return id;
	}

	public UserResponseDto getUser() {
		return user;
	}

	public ComposerResponseDto getComposer() {
		return composer;
	}
}
