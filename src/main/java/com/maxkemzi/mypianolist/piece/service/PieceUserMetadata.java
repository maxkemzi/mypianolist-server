package com.maxkemzi.mypianolist.piece.service;

import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

public class PieceUserMetadata {
	private boolean inFavorites;
	private UserPieceStatus status;

	protected PieceUserMetadata() {
	}

	public PieceUserMetadata(boolean inFavorites, UserPieceStatus status) {
		this.inFavorites = inFavorites;
		this.status = status;
	}

	public boolean getInFavorites() {
		return inFavorites;
	}

	public UserPieceStatus getStatus() {
		return status;
	}
}
