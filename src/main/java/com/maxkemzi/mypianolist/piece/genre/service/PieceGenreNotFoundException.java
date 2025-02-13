package com.maxkemzi.mypianolist.piece.genre.service;

public class PieceGenreNotFoundException extends RuntimeException {
	public PieceGenreNotFoundException() {
		super("The piece genre was not found.");
	}
}
