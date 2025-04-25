package com.maxkemzi.mypianolist.piece.genre.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GenreRequest {
	@NotBlank(message = "Name is required.")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters.")
	private String name;

	private String image;

	protected GenreRequest() {
	}

	public GenreRequest(String name, String image) {
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
