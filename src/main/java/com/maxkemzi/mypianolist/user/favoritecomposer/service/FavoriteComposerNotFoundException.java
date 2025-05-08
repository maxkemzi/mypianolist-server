package com.maxkemzi.mypianolist.user.favoritecomposer.service;

public class FavoriteComposerNotFoundException extends RuntimeException {
	public FavoriteComposerNotFoundException() {
		super("The favorite composer was not found.");
	}
}
