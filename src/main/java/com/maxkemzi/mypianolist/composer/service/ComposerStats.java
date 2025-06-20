package com.maxkemzi.mypianolist.composer.service;

public class ComposerStats {
	private long favorites;

	protected ComposerStats() {
	}

	public ComposerStats(long favorites) {
		this.favorites = favorites;
	}

	public long getFavorites() {
		return favorites;
	}
}
