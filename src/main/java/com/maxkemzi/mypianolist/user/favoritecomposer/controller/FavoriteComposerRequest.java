package com.maxkemzi.mypianolist.user.favoritecomposer.controller;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class FavoriteComposerRequest {
	@NotNull(message = "Composer id is required.")
	private UUID composerId;

	protected FavoriteComposerRequest() {
	}

	public FavoriteComposerRequest(UUID composerId) {
		this.composerId = composerId;
	}

	public UUID getComposerId() {
		return composerId;
	}
}
