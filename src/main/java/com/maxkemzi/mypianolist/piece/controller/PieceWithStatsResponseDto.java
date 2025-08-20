
package com.maxkemzi.mypianolist.piece.controller;

import com.maxkemzi.mypianolist.piece.service.PieceWithStats;

public class PieceWithStatsResponseDto extends PieceResponseDto {
	private Long learners;
	private Long favorites;

	protected PieceWithStatsResponseDto() {
	}

	public PieceWithStatsResponseDto(PieceWithStats pws) {
		super(pws.getPiece());

		if (pws.getStats() != null) {
			this.learners = pws.getStats().getLearners();
			this.favorites = pws.getStats().getFavorites();
		}
	}

	public Long getLearners() {
		return learners;
	}

	public Long getFavorites() {
		return favorites;
	}
}
