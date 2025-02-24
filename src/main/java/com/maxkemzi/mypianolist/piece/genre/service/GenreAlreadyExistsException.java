package com.maxkemzi.mypianolist.piece.genre.service;

public class GenreAlreadyExistsException extends RuntimeException {
	public GenreAlreadyExistsException() {
		super("The genre already exists.");
	}
}
