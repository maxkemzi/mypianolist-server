package com.maxkemzi.mypianolist.user.favouritecomposer.controller;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class UserFavouriteComposerRequest {
	@NotNull(message = "Composer id is required.")
	private UUID composerId;

	protected UserFavouriteComposerRequest() {
	}

	public UserFavouriteComposerRequest(UUID composerId) {
		this.composerId = composerId;
	}

	public UUID getComposerId() {
		return composerId;
	}
}
