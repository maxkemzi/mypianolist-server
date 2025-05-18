package com.maxkemzi.mypianolist.piece.service;

import java.time.LocalDate;
import java.util.UUID;

public class PieceCreatePayload {
	private String title;
	private String description;
	private LocalDate composedAt;
	private UUID composerId;
	private UUID genreId;

	protected PieceCreatePayload() {
	}

	public PieceCreatePayload(String title, String description, LocalDate composedAt, UUID composerId,
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
