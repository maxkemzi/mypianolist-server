package com.maxkemzi.mypianolist.user.favoritecomposer.service;

public class FavoriteComposerReachedLimitException extends RuntimeException {
	public FavoriteComposerReachedLimitException() {
		super("The maximum amount of favorite composers is 10.");
	}
}
