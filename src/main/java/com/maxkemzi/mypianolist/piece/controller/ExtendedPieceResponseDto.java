package com.maxkemzi.mypianolist.piece.controller;

import com.maxkemzi.mypianolist.piece.service.ExtendedPiece;

public class ExtendedPieceResponseDto extends PieceResponseDto {
	private long favourites;
	private long learners;

	protected ExtendedPieceResponseDto() {
	}

	public ExtendedPieceResponseDto(ExtendedPiece ep) {
		super(ep.getPiece());

		this.favourites = ep.getFavourites();
		this.learners = ep.getLearners();
	}

	public long getFavourites() {
		return favourites;
	}

	public long getLearners() {
		return learners;
	}
}
