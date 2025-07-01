
package com.maxkemzi.mypianolist.user.favoritepiece.service;

public class FavoritePieceReachedLimitException extends RuntimeException {
	public FavoritePieceReachedLimitException() {
		super("The maximum amount of favorite pieces is 10.");
	}
}
