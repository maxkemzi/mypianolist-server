package com.maxkemzi.mypianolist.piece.genre.model;

import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PieceGenre {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String name;

	protected PieceGenre() {
	}

	public PieceGenre(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		PieceGenre pieceGenre = (PieceGenre) o;
		return Objects.equals(name, pieceGenre.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
