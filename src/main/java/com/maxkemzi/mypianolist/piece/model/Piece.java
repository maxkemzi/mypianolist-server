package com.maxkemzi.mypianolist.piece.model;

import java.time.LocalDate;
import java.util.Objects;

import com.maxkemzi.mypianolist.composer.model.Composer;
import com.maxkemzi.mypianolist.db.BaseEntity;
import com.maxkemzi.mypianolist.piece.genre.model.Genre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@Column(name = "composed_at", nullable = false)
	private LocalDate composedAt;

	@ManyToOne
	@JoinColumn(name = "genre_id")
	private Genre genre;

	@ManyToOne
	@JoinColumn(name = "composer_id")
	private Composer composer;

	protected Piece() {
	}

	public Piece(String title, String description, LocalDate composedAt, Genre genre,
			Composer composer) {
		this.title = title;
		this.description = description;
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

	public LocalDate getComposedAt() {
		return composedAt;
	}

	public Genre getGenre() {
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
				+ ", getTitle()=" + getTitle() + ", getDescription()=" + getDescription()
				+ ", getComposedAt()=" + getComposedAt() + ", getGenre()=" + getGenre() + ", getComposer()=" + getComposer()
				+ ", getCreatedAt()=" + getCreatedAt() + ", getUpdatedAt()=" + getUpdatedAt()
				+ "]";
	}
}
