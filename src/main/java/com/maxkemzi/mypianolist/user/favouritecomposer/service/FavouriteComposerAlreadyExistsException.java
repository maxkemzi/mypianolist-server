package com.maxkemzi.mypianolist.user.favouritecomposer.service;

public class FavouriteComposerAlreadyExistsException extends RuntimeException {
	public FavouriteComposerAlreadyExistsException() {
		super("The favourite composer already exists.");
	}
}
