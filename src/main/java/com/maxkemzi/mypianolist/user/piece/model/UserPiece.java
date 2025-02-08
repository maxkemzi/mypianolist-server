package com.maxkemzi.mypianolist.user.piece.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.user.model.UserAccount;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "user_piece", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "user_id", "piece_id" })
})
public class UserPiece {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private Integer score;

	@Enumerated(EnumType.STRING)
	private UserPieceStatus status;

	private LocalDate startedAt;
	private LocalDate finishedAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserAccount user;

	@ManyToOne
	@JoinColumn(name = "piece_id", nullable = false)
	private Piece piece;

	protected UserPiece() {
	}

	public UserPiece(Integer score, UserPieceStatus status, LocalDate startedAt, LocalDate finishedAt, UserAccount user,
			Piece piece) {
		this.score = score;
		this.status = status;
		this.startedAt = startedAt;
		this.finishedAt = finishedAt;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.user = user;
		this.piece = piece;
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

	public UUID getId() {
		return id;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public UserPieceStatus getStatus() {
		return status;
	}

	public void setStatus(UserPieceStatus status) {
		this.status = status;
	}

	public LocalDate getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(LocalDate startedAt) {
		this.startedAt = startedAt;
	}

	public LocalDate getFinishedAt() {
		return finishedAt;
	}

	public void setFinishedAt(LocalDate finishedAt) {
		this.finishedAt = finishedAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public UserAccount getUser() {
		return user;
	}

	public Piece getPiece() {
		return piece;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		UserPiece userPiece = (UserPiece) o;
		return Objects.equals(user, userPiece.user) && Objects.equals(piece, userPiece.piece);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, piece);
	}
}
