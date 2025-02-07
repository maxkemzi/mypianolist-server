package com.maxkemzi.mypianolist.piece.controller;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class PieceDTO {
	@NotBlank(message = "Title is required.")
	private String title;

	@NotBlank(message = "Description is required.")
	private String description;

	private String image;

	@Past(message = "Composed date must be in the past.")
	private LocalDate composedAt;

	@NotNull(message = "Composer id is required.")
	private UUID composerId;

	@NotNull(message = "Genre id is required.")
	private UUID genreId;

	protected PieceDTO() {
	}

	public PieceDTO(String title, String description, String image, LocalDate composedAt, UUID composerId,
			UUID genreId) {
		this.title = title;
		this.description = description;
		this.image = image;
		this.composedAt = composedAt;
		this.composerId = composerId;
		this.genreId = genreId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getImage() {
		return image;
	}

	public LocalDate getComposedAt() {
		return composedAt;
	}

	public UUID getComposerId() {
		return composerId;
	}

	public UUID getGenreId() {
		return genreId;
	}
}
