package com.maxkemzi.mypianolist.user.favoritecomposer.service;

public class FavoriteComposerAlreadyExistsException extends RuntimeException {
	public FavoriteComposerAlreadyExistsException() {
		super("The favorite composer already exists.");
	}
}
