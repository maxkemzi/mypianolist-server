package com.maxkemzi.mypianolist.user.favoritepiece.service;

public class FavoritePieceAlreadyExistsException extends RuntimeException {
	public FavoritePieceAlreadyExistsException() {
		super("The favorite piece already exists.");
	}
}
