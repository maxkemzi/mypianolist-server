package com.maxkemzi.mypianolist.user.favoritecomposer.service;

import java.util.UUID;

public class FavoriteComposerCreatePayload {
	private String username;
	private UUID composerId;

	protected FavoriteComposerCreatePayload() {
	}

	public FavoriteComposerCreatePayload(String username, UUID composerId) {
		this.username = username;
		this.composerId = composerId;
	}

	public String getUsername() {
		return username;
	}

	public UUID getComposerId() {
		return composerId;
	}
}
