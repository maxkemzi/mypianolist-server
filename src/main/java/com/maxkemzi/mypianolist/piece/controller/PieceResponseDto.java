package com.maxkemzi.mypianolist.piece.controller;

import java.time.LocalDate;
import java.util.UUID;

import com.maxkemzi.mypianolist.composer.controller.ComposerResponseDto;
import com.maxkemzi.mypianolist.piece.genre.controller.GenreResponseDto;
import com.maxkemzi.mypianolist.piece.model.Piece;

public class PieceResponseDto {
	private UUID id;
	private String title;
	private String description;
	private LocalDate composedAt;
	private GenreResponseDto genre;
	private ComposerResponseDto composer;

	protected PieceResponseDto() {
	}

	public PieceResponseDto(Piece p) {
		this.id = p.getId();
		this.title = p.getTitle();
		this.description = p.getDescription();
		this.composedAt = p.getComposedAt();
		this.genre = new GenreResponseDto(p.getGenre());
		this.composer = new ComposerResponseDto(p.getComposer());
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

	public LocalDate getComposedAt() {
		return composedAt;
	}

	public GenreResponseDto getGenre() {
		return genre;
	}

	public ComposerResponseDto getComposer() {
		return composer;
	}
}
