package com.maxkemzi.mypianolist.user.favouritecomposer.service;

import java.util.UUID;

public class FavouriteComposerCreatePayload {
	private String username;
	private UUID composerId;

	protected FavouriteComposerCreatePayload() {
	}

	public FavouriteComposerCreatePayload(String username, UUID composerId) {
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
