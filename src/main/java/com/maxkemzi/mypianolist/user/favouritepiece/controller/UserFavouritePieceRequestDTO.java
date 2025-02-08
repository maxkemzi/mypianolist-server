package com.maxkemzi.mypianolist.user.favouritepiece.controller;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class UserFavouritePieceRequestDTO {
	@NotNull(message = "Piece id is required.")
	private UUID pieceId;

	protected UserFavouritePieceRequestDTO() {
	}

	public UserFavouritePieceRequestDTO(UUID pieceId) {
		this.pieceId = pieceId;
	}

	public UUID getPieceId() {
		return pieceId;
	}
}
