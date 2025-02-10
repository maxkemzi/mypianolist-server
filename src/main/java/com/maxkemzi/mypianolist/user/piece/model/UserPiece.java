package com.maxkemzi.mypianolist.user.piece.model;

import java.time.LocalDate;
import java.util.Objects;

import com.maxkemzi.mypianolist.db.BaseEntity;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "user_piece", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "user_id", "piece_id" })
})
public class UserPiece extends BaseEntity {
	@Column(name = "score")
	private Integer score;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private UserPieceStatus status;

	@Column(name = "started_at")
	private LocalDate startedAt;

	@Column(name = "finished_at")
	private LocalDate finishedAt;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "piece_id", nullable = false)
	private Piece piece;

	protected UserPiece() {
	}

	public UserPiece(Integer score, UserPieceStatus status, LocalDate startedAt, LocalDate finishedAt, User user,
			Piece piece) {
		this.score = score;
		this.status = status;
		this.startedAt = startedAt;
		this.finishedAt = finishedAt;
		this.user = user;
		this.piece = piece;
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

	public User getUser() {
		return user;
	}

	public Piece getPiece() {
		return piece;
	}

	protected boolean entityEquals(Object o) {
		UserPiece up = (UserPiece) o;
		return Objects.equals(user, up.user) && Objects.equals(piece, up.piece);
	}

	protected Object[] getHashCodeValues() {
		return new Object[] { user, piece };
	}
}
