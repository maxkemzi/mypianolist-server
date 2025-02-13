package com.maxkemzi.mypianolist.piece.genre.service;

public class PieceGenreDoesntExistException extends RuntimeException {
	public PieceGenreDoesntExistException() {
		super("Piece genre doesn't exist.");
	}
}
