package com.maxkemzi.mypianolist.piece.service;

public class PieceStats {
	private long learners;
	private long favorites;

	protected PieceStats() {
	}

	public PieceStats(long learners, long favorites) {
		this.learners = learners;
		this.favorites = favorites;
	}

	public long getLearners() {
		return learners;
	}

	public long getFavorites() {
		return favorites;
	}
}
