package com.maxkemzi.mypianolist.piece.controller;

import java.time.LocalDate;
import java.util.UUID;

import com.maxkemzi.mypianolist.composer.controller.ComposerResponseDto;
import com.maxkemzi.mypianolist.piece.genre.controller.PieceGenreResponseDto;
import com.maxkemzi.mypianolist.piece.model.Piece;

public class PieceResponseDto {
	private UUID id;
	private String title;
	private String description;
	private String image;
	private LocalDate composedAt;
	private PieceGenreResponseDto genre;
	private ComposerResponseDto composer;

	protected PieceResponseDto() {
	}

	public PieceResponseDto(Piece piece) {
		this.id = piece.getId();
		this.title = piece.getTitle();
		this.description = piece.getDescription();
		this.image = piece.getImage();
		this.composedAt = piece.getComposedAt();
		this.genre = new PieceGenreResponseDto(piece.getGenre());
		this.composer = new ComposerResponseDto(piece.getComposer());
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

	public PieceGenreResponseDto getGenre() {
		return genre;
	}

	public ComposerResponseDto getComposer() {
		return composer;
	}
}
