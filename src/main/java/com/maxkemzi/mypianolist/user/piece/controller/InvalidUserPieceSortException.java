package com.maxkemzi.mypianolist.user.piece.controller;

public class InvalidUserPieceSortException extends RuntimeException {
	public InvalidUserPieceSortException() {
		super("Invalid sort value. Allowed values: created_at, score.");
	}
}
