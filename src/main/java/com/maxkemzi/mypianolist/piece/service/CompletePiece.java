package com.maxkemzi.mypianolist.piece.service;

import com.maxkemzi.mypianolist.piece.model.Piece;

public class CompletePiece {
	private Piece piece;
	private PieceStats stats;
	private PieceUserMetadata userMetadata;

	protected CompletePiece() {
	}

	public CompletePiece(Piece p, PieceStats s, PieceUserMetadata um) {
		this.piece = p;
		this.stats = s;
		this.userMetadata = um;
	}

	public Piece getPiece() {
		return piece;
	}

	public PieceStats getStats() {
		return stats;
	}

	public PieceUserMetadata getUserMetadata() {
		return userMetadata;
	}
}
