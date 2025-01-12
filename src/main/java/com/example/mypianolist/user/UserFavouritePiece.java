package com.example.mypianolist.user;

import java.util.UUID;

import com.example.mypianolist.piece.Piece;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserFavouritePiece {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "piece_id", nullable = false)
	private Piece piece;

	public UserFavouritePiece() {
	}

	public User getUser() {
		return this.user;
	}

	public Piece getPiece() {
		return this.piece;
	}
}
