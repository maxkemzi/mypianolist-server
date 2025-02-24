package com.maxkemzi.mypianolist.user.favouritepiece.service;

public class FavouritePieceAlreadyExistsException extends RuntimeException {
	public FavouritePieceAlreadyExistsException() {
		super("The favourite piece already exists.");
	}
}
