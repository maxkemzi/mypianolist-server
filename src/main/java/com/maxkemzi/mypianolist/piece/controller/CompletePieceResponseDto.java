package com.maxkemzi.mypianolist.piece.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maxkemzi.mypianolist.piece.service.CompletePiece;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompletePieceResponseDto extends PieceResponseDto {
	private Long favorites;
	private Long learners;
	private Boolean inFavorites;

	protected CompletePieceResponseDto() {
	}

	public CompletePieceResponseDto(CompletePiece p) {
		super(p.getPiece());

		if (p.getStats() != null) {
			this.favorites = p.getStats().getFavorites();
			this.learners = p.getStats().getLearners();
		}

		if (p.getUserMetadata() != null) {
			this.inFavorites = p.getUserMetadata().getInFavorites();
		}
	}

	public Long getFavorites() {
		return favorites;
	}

	public Long getLearners() {
		return learners;
	}

	public Boolean getInFavorites() {
		return inFavorites;
	}
}
