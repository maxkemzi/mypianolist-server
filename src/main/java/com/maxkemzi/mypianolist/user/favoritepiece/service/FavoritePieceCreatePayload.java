package com.maxkemzi.mypianolist.user.favoritepiece.service;

import java.util.UUID;

public class FavoritePieceCreatePayload {
	private String username;
	private UUID pieceId;

	protected FavoritePieceCreatePayload() {
	}

	public FavoritePieceCreatePayload(String username, UUID pieceId) {
		this.username = username;
		this.pieceId = pieceId;
	}

	public String getUsername() {
		return username;
	}

	public UUID getPieceId() {
		return pieceId;
	}
}
