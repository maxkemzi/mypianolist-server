package com.maxkemzi.mypianolist.user.piece.model;

public class InvalidUserPieceStatusException extends RuntimeException {
	public InvalidUserPieceStatusException() {
		super("Invalid status value. Allowed values: dropped, completed, plan_to_learn, currently_learning, on_hold.");
	}
}
