package com.maxkemzi.mypianolist.piece.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.user.model.UserFavouritePiece;
import com.maxkemzi.mypianolist.user.model.UserPiece;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "piece", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "title", "composer_id" })
})
public class Piece {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;

	private String image;

	@Column(nullable = false)
	private LocalDate composedAt;

	@ManyToOne
	@JoinColumn(name = "genre_id")
	private PieceGenre genre;

	@ManyToOne
	@JoinColumn(name = "composer_id", nullable = false)
	private Composer composer;

	@OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserPiece> userPieces = new ArrayList<>();

	@OneToMany(mappedBy = "piece", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserFavouritePiece> favouritePieces = new ArrayList<>();

	protected Piece() {
	}

	public Piece(String title, String description, String image, LocalDate composedAt, PieceGenre genre,
			Composer composer) {
		this.title = title;
		this.description = description;
		this.image = image;
		this.composedAt = composedAt;
		this.genre = genre;
		this.composer = composer;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Piece piece = (Piece) o;
		return Objects.equals(title, piece.title) && Objects.equals(composer, piece.composer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, composer);
	}
}
