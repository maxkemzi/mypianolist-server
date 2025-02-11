package com.maxkemzi.mypianolist.piece.genre.controller;

import java.util.UUID;

import com.maxkemzi.mypianolist.piece.genre.entity.PieceGenre;

public class PieceGenreResponseDTO {
	private UUID id;
	private String name;

	protected PieceGenreResponseDTO() {
	}

	public PieceGenreResponseDTO(PieceGenre genre) {
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
