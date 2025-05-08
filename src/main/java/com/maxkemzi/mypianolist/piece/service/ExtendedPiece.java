package com.maxkemzi.mypianolist.piece.service;

import com.maxkemzi.mypianolist.piece.model.Piece;

public class ExtendedPiece {
	private Piece piece;
	private long favourites;
	private long learners;

	protected ExtendedPiece() {
	}

	public ExtendedPiece(Piece piece, long favourites, long learners) {
		this.piece = piece;
		this.favourites = favourites;
		this.learners = learners;
	}

	public Piece getPiece() {
		return piece;
	}

	public long getFavourites() {
		return favourites;
	}

	public long getLearners() {
		return learners;
	}
}
