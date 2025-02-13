package com.maxkemzi.mypianolist.piece.service;

public class PieceNotFoundException extends RuntimeException {
	public PieceNotFoundException() {
		super("The piece was not found.");
	}
}
