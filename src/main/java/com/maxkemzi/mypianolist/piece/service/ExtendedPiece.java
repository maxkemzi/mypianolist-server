package com.maxkemzi.mypianolist.piece.service;

import com.maxkemzi.mypianolist.piece.model.Piece;

public class ExtendedPiece {
	private Piece piece;
	private long favorites;
	private long learners;

	protected ExtendedPiece() {
	}

	public ExtendedPiece(Piece piece, long favorites, long learners) {
		this.piece = piece;
		this.favorites = favorites;
		this.learners = learners;
	}

	public Piece getPiece() {
		return piece;
	}

	public long getFavorites() {
		return favorites;
	}

	public long getLearners() {
		return learners;
	}
}
