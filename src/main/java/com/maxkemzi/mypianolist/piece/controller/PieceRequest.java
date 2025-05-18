package com.maxkemzi.mypianolist.piece.controller;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class PieceRequest {
	@NotBlank(message = "Title is required.")
	@Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters.")
	private String title;

	@NotBlank(message = "Description is required.")
	@Size(min = 10, max = 1000, message = "Description must be between 10 and 1000 characters.")
	private String description;

	@NotNull(message = "Composed date is required.")
	@Past(message = "Composed date must be in the past.")
	private LocalDate composedAt;

	@NotNull(message = "Composer id is required.")
	private UUID composerId;

	@NotNull(message = "Genre id is required.")
	private UUID genreId;

	protected PieceRequest() {
	}

	public PieceRequest(String title, String description, LocalDate composedAt, UUID composerId,
			UUID genreId) {
		this.title = title;
		this.description = description;
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
