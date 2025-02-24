package com.maxkemzi.mypianolist.user.favouritecomposer.controller;

import java.util.UUID;

import com.maxkemzi.mypianolist.composer.controller.ComposerResponseDto;
import com.maxkemzi.mypianolist.user.controller.UserResponseDto;
import com.maxkemzi.mypianolist.user.favouritecomposer.model.FavouriteComposer;

public class FavouriteComposerResponseDto {
	private UUID id;
	private UserResponseDto user;
	private ComposerResponseDto composer;

	protected FavouriteComposerResponseDto() {
	}

	public FavouriteComposerResponseDto(FavouriteComposer fc) {
		this.id = fc.getId();
		this.user = new UserResponseDto(fc.getUser());
		this.composer = new ComposerResponseDto(fc.getComposer());
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
