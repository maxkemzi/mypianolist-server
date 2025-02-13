package com.maxkemzi.mypianolist.piece.genre.service;

public class PieceGenreCreatePayload {
	private String name;

	protected PieceGenreCreatePayload() {
	}

	public PieceGenreCreatePayload(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
