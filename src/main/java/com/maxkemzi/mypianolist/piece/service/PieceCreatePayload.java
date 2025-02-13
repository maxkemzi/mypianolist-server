package com.maxkemzi.mypianolist.piece.service;

import java.time.LocalDate;
import java.util.UUID;

public class PieceCreatePayload {
	private String title;
	private String description;
	private String image;
	private LocalDate composedAt;
	private UUID composerId;
	private UUID genreId;

	protected PieceCreatePayload() {
	}

	public PieceCreatePayload(String title, String description, String image, LocalDate composedAt, UUID composerId,
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
