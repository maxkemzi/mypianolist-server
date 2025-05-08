package com.maxkemzi.mypianolist.user.favoritepiece.service;

public class FavoritePieceNotFoundException extends RuntimeException {
	public FavoritePieceNotFoundException() {
		super("The favorite piece was not found.");
	}
}
