package com.maxkemzi.mypianolist.piece.service;

import com.maxkemzi.mypianolist.piece.model.Piece;

public class PieceWithStats {
	private Piece piece;
	private PieceStats stats;

	protected PieceWithStats() {
	}

	public PieceWithStats(Piece p, long learners, long favorites) {
		this.piece = p;
		this.stats = new PieceStats(learners, favorites);
	}

	public Piece getPiece() {
		return piece;
	}

	public PieceStats getStats() {
		return stats;
	}
}
