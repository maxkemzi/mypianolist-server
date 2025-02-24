package com.maxkemzi.mypianolist.user.favouritepiece.service;

import java.util.UUID;

public class FavouritePieceCreatePayload {
	private String username;
	private UUID pieceId;

	protected FavouritePieceCreatePayload() {
	}

	public FavouritePieceCreatePayload(String username, UUID pieceId) {
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
