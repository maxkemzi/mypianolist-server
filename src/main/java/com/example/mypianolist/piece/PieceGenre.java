package com.example.mypianolist.piece;

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

	public PieceGenre() {
	}

	public PieceGenre(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
