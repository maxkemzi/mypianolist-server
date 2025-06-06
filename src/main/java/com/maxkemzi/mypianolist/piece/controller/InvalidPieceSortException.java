package com.maxkemzi.mypianolist.piece.controller;

public class InvalidPieceSortException extends RuntimeException {
	public InvalidPieceSortException() {
		super("Invalid sort value. Allowed values: created_at, learners, favorites.");
	}
}
