package com.maxkemzi.mypianolist.piece.genre.service;

public class GenreCreatePayload {
	private String name;
	private String image;

	protected GenreCreatePayload() {
	}

	public GenreCreatePayload(String name, String image) {
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public String getImage() {
		return image;
	}
}
