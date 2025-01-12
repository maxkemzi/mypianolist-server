package com.example.mypianolist.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.mypianolist.piece.Piece;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserPiece {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "piece_id", nullable = false)
	private Piece piece;

	private Integer score;

	@Enumerated(EnumType.STRING)
	private UserPieceStatus status;

	private LocalDate startedAt;
	private LocalDate finishedAt;
	private LocalDateTime updatedAt;
	private LocalDateTime createdAt;

	public UserPiece() {
	}

	public UserPiece(User user, Piece piece, UserPieceStatus status) {
		this.user = user;
		this.piece = piece;
		this.status = status;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public UUID getId() {
		return this.id;
	}

	public User getUser() {
		return this.user;
	}

	public Piece getPiece() {
		return this.piece;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public UserPieceStatus getStatus() {
		return this.status;
	}

	public void setStatus(UserPieceStatus status) {
		this.status = status;
	}

	public LocalDate getStartedAt() {
		return this.startedAt;
	}

	public void setStartedAt(LocalDate startedAt) {
		this.startedAt = startedAt;
	}

	public LocalDate getFinishedAt() {
		return this.finishedAt;
	}

	public void setFinishedAt(LocalDate finishedAt) {
		this.finishedAt = finishedAt;
	}
}
