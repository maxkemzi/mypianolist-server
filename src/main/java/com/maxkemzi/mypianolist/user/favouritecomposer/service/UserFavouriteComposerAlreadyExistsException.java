package com.maxkemzi.mypianolist.user.favouritecomposer.service;

public class UserFavouriteComposerAlreadyExistsException extends RuntimeException {
	public UserFavouriteComposerAlreadyExistsException() {
		super("The user favourite composer already exists.");
	}
}
