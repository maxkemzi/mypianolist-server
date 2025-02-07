package com.maxkemzi.mypianolist.piece.genre.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PieceGenreRequestDTO {
	@NotBlank(message = "Name is required.")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters.")
	private String name;

	protected PieceGenreRequestDTO() {
	}

	public PieceGenreRequestDTO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
