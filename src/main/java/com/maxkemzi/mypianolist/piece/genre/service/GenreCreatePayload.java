package com.maxkemzi.mypianolist.piece.genre.service;

public class GenreCreatePayload {
	private String name;

	protected GenreCreatePayload() {
	}

	public GenreCreatePayload(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
