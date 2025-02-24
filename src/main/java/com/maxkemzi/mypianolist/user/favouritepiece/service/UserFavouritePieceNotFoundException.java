package com.maxkemzi.mypianolist.user.favouritepiece.service;

public class UserFavouritePieceNotFoundException extends RuntimeException {
	public UserFavouritePieceNotFoundException() {
		super("The user favourite piece was not found.");
	}
}
