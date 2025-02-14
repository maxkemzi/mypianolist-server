package com.maxkemzi.mypianolist.piece.genre.controller;

import java.util.UUID;

import com.maxkemzi.mypianolist.piece.genre.model.PieceGenre;

public class PieceGenreResponseDto {
	private UUID id;
	private String name;

	protected PieceGenreResponseDto() {
	}

	public PieceGenreResponseDto(PieceGenre genre) {
		this.id = genre.getId();
		this.name = genre.getName();
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
