package com.maxkemzi.mypianolist.composer.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maxkemzi.mypianolist.composer.service.CompleteComposer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompleteComposerResponseDto extends ComposerResponseDto {
	private Long favorites;
	private Boolean inFavorites;

	protected CompleteComposerResponseDto() {
	}

	public CompleteComposerResponseDto(CompleteComposer p) {
		super(p.getComposer());

		if (p.getStats() != null) {
			this.favorites = p.getStats().getFavorites();
		}

		if (p.getUserMetadata() != null) {
			this.inFavorites = p.getUserMetadata().getInFavorites();
		}
	}

	public Long getFavorites() {
		return favorites;
	}

	public Boolean getInFavorites() {
		return inFavorites;
	}
}
