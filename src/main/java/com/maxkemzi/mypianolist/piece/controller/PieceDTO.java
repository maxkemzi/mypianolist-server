package com.maxkemzi.mypianolist.piece.controller;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class PieceDTO {
	@NotBlank(message = "Title is required.")
	public String title;

	@NotBlank(message = "Description is required.")
	public String description;

	public String image;

	@Past(message = "Composed date must be in the past.")
	public LocalDate composedAt;

	@NotNull(message = "Composer id is required.")
	public UUID composerId;

	@NotNull(message = "Genre id is required.")
	public UUID genreId;

	public PieceDTO() {
	}
}
