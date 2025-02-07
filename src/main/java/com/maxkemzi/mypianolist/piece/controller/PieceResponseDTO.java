package com.maxkemzi.mypianolist.piece.controller;

import java.time.LocalDate;
import java.util.UUID;

import com.maxkemzi.mypianolist.composer.controller.ComposerResponseDTO;
import com.maxkemzi.mypianolist.piece.model.Piece;

public class PieceResponseDTO {
	private UUID id;
	private String title;
	private String description;
	private String image;
	private LocalDate composedAt;
	private PieceGenreResponseDTO genre;
	private ComposerResponseDTO composer;

	protected PieceResponseDTO() {
	}

	public PieceResponseDTO(Piece piece) {
		this.id = piece.getId();
		this.title = piece.getTitle();
		this.description = piece.getDescription();
		this.image = piece.getImage();
		this.composedAt = piece.getComposedAt();
		this.genre = new PieceGenreResponseDTO(piece.getGenre());
		this.composer = new ComposerResponseDTO(piece.getComposer());
	}

	public UUID getId() {
		return id;
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

	public PieceGenreResponseDTO getGenre() {
		return genre;
	}

	public ComposerResponseDTO getComposer() {
		return composer;
	}
}
