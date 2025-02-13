package com.maxkemzi.mypianolist.user.piece.controller;

import java.time.LocalDate;
import java.util.UUID;

import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public class UserPieceRequest {
	@Min(value = 0, message = "Score must be at least 0.")
	@Max(value = 10, message = "Score must not exceed 10.")
	private Integer score;

	private UserPieceStatus status;

	@PastOrPresent(message = "Started date must be in the past or present.")
	private LocalDate startedAt;

	@PastOrPresent(message = "Finished date must be in the past or present.")
	private LocalDate finishedAt;

	@NotNull(message = "Piece id is required.")
	private UUID pieceId;

	protected UserPieceRequest() {
	}

	public UserPieceRequest(Integer score, UserPieceStatus status, LocalDate startedAt, LocalDate finishedAt,
			UUID pieceId) {
		this.score = score;
		this.status = status;
		this.startedAt = startedAt;
		this.finishedAt = finishedAt;
		this.pieceId = pieceId;
	}

	public Integer getScore() {
		return score;
	}

	public UserPieceStatus getStatus() {
		return status;
	}

	public LocalDate getStartedAt() {
		return startedAt;
	}

	public LocalDate getFinishedAt() {
		return finishedAt;
	}

	public UUID getPieceId() {
		return pieceId;
	}
}
