package com.maxkemzi.mypianolist.user.piece.service;

public class UserPieceAlreadyExistsException extends RuntimeException {
	public UserPieceAlreadyExistsException() {
		super("The user piece already exists.");
	}
}
