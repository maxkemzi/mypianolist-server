package com.maxkemzi.mypianolist.piece.service;

public class CompletePiece {
	private PieceWithStats piece;
	private PieceUserMetadata userMetadata;

	protected CompletePiece() {
	}

	public CompletePiece(PieceWithStats p, PieceUserMetadata um) {
		this.piece = p;
		this.userMetadata = um;
	}

	public PieceWithStats getPieceWithStats() {
		return piece;
	}

	public PieceUserMetadata getUserMetadata() {
		return userMetadata;
	}
}
