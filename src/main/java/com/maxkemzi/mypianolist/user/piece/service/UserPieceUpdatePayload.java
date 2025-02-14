package com.maxkemzi.mypianolist.user.piece.service;

import java.time.LocalDate;

import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

public class UserPieceUpdatePayload {
	private Integer score;
	private UserPieceStatus status;
	private LocalDate startedAt;
	private LocalDate finishedAt;

	protected UserPieceUpdatePayload() {
	}

	public UserPieceUpdatePayload(Integer score, UserPieceStatus status, LocalDate startedAt, LocalDate finishedAt) {
		this.score = score;
		this.status = status;
		this.startedAt = startedAt;
		this.finishedAt = finishedAt;
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
}
