package com.maxkemzi.mypianolist.user.favoritepiece.controller;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class FavoritePieceRequest {
	@NotNull(message = "Piece id is required.")
	private UUID pieceId;

	protected FavoritePieceRequest() {
	}

	public FavoritePieceRequest(UUID pieceId) {
		this.pieceId = pieceId;
	}

	public UUID getPieceId() {
		return pieceId;
	}
}
