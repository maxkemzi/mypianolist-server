package com.maxkemzi.mypianolist.piece.service;

public class PieceStats {
	private long favorites;
	private long learners;

	protected PieceStats() {
	}

	public PieceStats(long favorites, long learners) {
		this.favorites = favorites;
		this.learners = learners;
	}

	public long getFavorites() {
		return favorites;
	}

	public long getLearners() {
		return learners;
	}
}
