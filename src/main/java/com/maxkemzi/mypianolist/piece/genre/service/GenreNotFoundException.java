package com.maxkemzi.mypianolist.piece.genre.service;

public class GenreNotFoundException extends RuntimeException {
	public GenreNotFoundException() {
		super("The genre was not found.");
	}
}
