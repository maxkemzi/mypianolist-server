package com.maxkemzi.mypianolist.piece.service;

public class PieceUserMetadata {
	private boolean inFavorites;

	protected PieceUserMetadata() {
	}

	public PieceUserMetadata(boolean inFavorites) {
		this.inFavorites = inFavorites;
	}

	public boolean getInFavorites() {
		return inFavorites;
	}
}
