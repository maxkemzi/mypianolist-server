package com.maxkemzi.mypianolist.user.favouritecomposer.service;

public class UserFavouriteComposerNotFoundException extends RuntimeException {
	public UserFavouriteComposerNotFoundException() {
		super("The user favourite composer was not found.");
	}
}
