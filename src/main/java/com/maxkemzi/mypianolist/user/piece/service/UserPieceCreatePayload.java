package com.maxkemzi.mypianolist.user.piece.service;

import java.time.LocalDate;
import java.util.UUID;

import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

public class UserPieceCreatePayload {
	private Integer score;
	private UserPieceStatus status;
	private LocalDate startedAt;
	private LocalDate finishedAt;
	private String username;
	private UUID pieceId;

	protected UserPieceCreatePayload() {
	}

	public UserPieceCreatePayload(Integer score, UserPieceStatus status, LocalDate startedAt, LocalDate finishedAt,
			String username, UUID pieceId) {
		this.score = score;
		this.status = status;
		this.startedAt = startedAt;
		this.finishedAt = finishedAt;
		this.username = username;
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

	public String getUsername() {
		return username;
	}

	public UUID getPieceId() {
		return pieceId;
	}
}
