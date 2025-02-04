package com.maxkemzi.mypianolist.user.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.maxkemzi.mypianolist.piece.model.Piece;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class UserFavouritePiece {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserAccount user;

	@ManyToOne
	@JoinColumn(name = "piece_id", nullable = false)
	private Piece piece;

	public UserFavouritePiece() {
	}

	public UserFavouritePiece(UserAccount user, Piece piece) {
		this.user = user;
		this.piece = piece;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public UserAccount getUser() {
		return this.user;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return this.updatedAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		UserFavouritePiece userFavouritePiece = (UserFavouritePiece) o;
		return id != null && id.equals(userFavouritePiece.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
