package com.maxkemzi.mypianolist.user.favouritepiece.service;

public class FavouritePieceNotFoundException extends RuntimeException {
	public FavouritePieceNotFoundException() {
		super("The favourite piece was not found.");
	}
}
