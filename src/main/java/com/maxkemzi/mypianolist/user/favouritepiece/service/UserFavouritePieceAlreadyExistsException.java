package com.maxkemzi.mypianolist.user.favouritepiece.service;

public class UserFavouritePieceAlreadyExistsException extends RuntimeException {
	public UserFavouritePieceAlreadyExistsException() {
		super("The user favourite piece already exists.");
	}
}
