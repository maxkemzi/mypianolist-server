package com.maxkemzi.mypianolist.user.favouritecomposer.service;

public class FavouriteComposerNotFoundException extends RuntimeException {
	public FavouriteComposerNotFoundException() {
		super("The favourite composer was not found.");
	}
}
