package com.maxkemzi.mypianolist.user.favouritecomposer.controller;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class FavouriteComposerRequest {
	@NotNull(message = "Composer id is required.")
	private UUID composerId;

	protected FavouriteComposerRequest() {
	}

	public FavouriteComposerRequest(UUID composerId) {
		this.composerId = composerId;
	}

	public UUID getComposerId() {
		return composerId;
	}
}
