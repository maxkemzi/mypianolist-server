package com.maxkemzi.mypianolist.piece.genre.service;

public class PieceGenreAlreadyExistsException extends RuntimeException {
	public PieceGenreAlreadyExistsException() {
		super("The piece genre already exists.");
	}
}
