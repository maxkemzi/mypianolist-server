package com.maxkemzi.mypianolist.piece.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.maxkemzi.mypianolist.composer.entity.Composer;
import com.maxkemzi.mypianolist.piece.genre.entity.PieceGenre;
import com.maxkemzi.mypianolist.user.favouritepiece.entity.UserFavouritePiece;
import com.maxkemzi.mypianolist.user.piece.entity.UserPiece;
import com.maxkemzi.mypianolist.db.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "piece", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "title", "composer_id" })
})
public class Piece extends BaseEntity {
	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false, columnDefinition = "TEXT")
	private String description;

	@Column(name = "image")
	private String image;

	@Column(name = "composed_at", nullable = false)
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

	public PieceGenre getGenre() {
		return genre;
	}

	public Composer getComposer() {
		return composer;
	}

	protected boolean entityEquals(Object o) {
		Piece p = (Piece) o;
		return Objects.equals(title, p.title) && Objects.equals(composer, p.composer);
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { title, composer };
	}

	@Override
	public String toString() {
		return "Piece [getId()=" + getId()
				+ ", getTitle()=" + getTitle() + ", getDescription()=" + getDescription() + ", getImage()=" + getImage()
				+ ", getComposedAt()=" + getComposedAt() + ", getGenre()=" + getGenre() + ", getComposer()=" + getComposer()
				+ ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()=" + getUpdatedAt()
				+ "]";
	}
}
