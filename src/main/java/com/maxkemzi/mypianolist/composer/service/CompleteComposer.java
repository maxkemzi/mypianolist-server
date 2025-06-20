package com.maxkemzi.mypianolist.composer.service;

import com.maxkemzi.mypianolist.composer.model.Composer;

public class CompleteComposer {
	private Composer composer;
	private ComposerStats stats;
	private ComposerUserMetadata userMetadata;

	protected CompleteComposer() {
	}

	public CompleteComposer(Composer c, ComposerStats s, ComposerUserMetadata um) {
		this.composer = c;
		this.stats = s;
		this.userMetadata = um;
	}

	public Composer getComposer() {
		return composer;
	}

	public ComposerStats getStats() {
		return stats;
	}

	public ComposerUserMetadata getUserMetadata() {
		return userMetadata;
	}
}
