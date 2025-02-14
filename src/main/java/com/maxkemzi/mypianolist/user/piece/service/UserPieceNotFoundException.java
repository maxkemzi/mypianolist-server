package com.maxkemzi.mypianolist.user.piece.service;

public class UserPieceNotFoundException extends RuntimeException {
	public UserPieceNotFoundException() {
		super("The user piece was not found.");
	}
}
