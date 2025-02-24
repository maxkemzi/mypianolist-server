package com.maxkemzi.mypianolist.user.favouritepiece.controller;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class FavouritePieceRequest {
	@NotNull(message = "Piece id is required.")
	private UUID pieceId;

	protected FavouritePieceRequest() {
	}

	public FavouritePieceRequest(UUID pieceId) {
		this.pieceId = pieceId;
	}

	public UUID getPieceId() {
		return pieceId;
	}
}
