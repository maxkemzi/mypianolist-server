package com.example.mypianolist.piece;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.mypianolist.composer.Composer;
import com.example.mypianolist.user.UserFavouritePiece;
import com.example.mypianolist.user.UserPiece;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Piece {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String image;

	@Column(nullable = false)
	private LocalDate composedAt;

	@ManyToOne
	@JoinColumn(name = "composer_id")
	private Composer composer;

	@ManyToOne
	@JoinColumn(name = "genre_id")
	private PieceGenre genre;

	@OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserPiece> userPieces = new ArrayList<>();

	@OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserFavouritePiece> favouritePieces = new ArrayList<>();

	public Piece() {
	}

	public Piece(String title, String description, String image, LocalDate composedAt, Composer composer,
			PieceGenre genre) {
		this.title = title;
		this.description = description;
		this.image = image;
		this.composedAt = composedAt;
		this.composer = composer;
		this.genre = genre;
	}

	public UUID getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description;
	}

	public String getImage() {
		return this.image;
	}

	public LocalDate getComposedAt() {
		return this.composedAt;
	}
}
