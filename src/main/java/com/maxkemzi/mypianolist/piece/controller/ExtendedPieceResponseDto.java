package com.maxkemzi.mypianolist.piece.controller;

import com.maxkemzi.mypianolist.piece.service.ExtendedPiece;

public class ExtendedPieceResponseDto extends PieceResponseDto {
	private long favorites;
	private long learners;

	protected ExtendedPieceResponseDto() {
	}

	public ExtendedPieceResponseDto(ExtendedPiece ep) {
		super(ep.getPiece());

		this.favorites = ep.getFavorites();
		this.learners = ep.getLearners();
	}

	public long getFavorites() {
		return favorites;
	}

	public long getLearners() {
		return learners;
	}
}
