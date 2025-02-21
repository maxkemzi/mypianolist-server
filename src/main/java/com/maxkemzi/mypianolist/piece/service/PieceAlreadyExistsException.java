package com.maxkemzi.mypianolist.piece.service;

public class PieceAlreadyExistsException extends RuntimeException {
	public PieceAlreadyExistsException() {
		super("The piece already exists.");
	}
}
