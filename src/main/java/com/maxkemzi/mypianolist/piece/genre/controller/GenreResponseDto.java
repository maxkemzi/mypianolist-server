package com.maxkemzi.mypianolist.piece.genre.controller;

import java.util.UUID;

import com.maxkemzi.mypianolist.piece.genre.model.Genre;

public class GenreResponseDto {
	private UUID id;
	private String name;

	protected GenreResponseDto() {
	}

	public GenreResponseDto(Genre g) {
		this.id = g.getId();
		this.name = g.getName();
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
