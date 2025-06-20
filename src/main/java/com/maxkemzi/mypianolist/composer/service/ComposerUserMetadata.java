package com.maxkemzi.mypianolist.composer.service;

public class ComposerUserMetadata {
	private boolean inFavorites;

	protected ComposerUserMetadata() {
	}

	public ComposerUserMetadata(boolean inFavorites) {
		this.inFavorites = inFavorites;
	}

	public boolean getInFavorites() {
		return inFavorites;
	}
}
