package com.maxkemzi.mypianolist.user.piece.controller;

import java.time.LocalDate;

import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;

public class UserPieceUpdateRequest {
	@Min(value = 0, message = "Score must be at least 0.")
	@Max(value = 10, message = "Score must not exceed 10.")
	private Integer score;

	private UserPieceStatus status;

	@PastOrPresent(message = "Started date must be in the past or present.")
	private LocalDate startedAt;

	@PastOrPresent(message = "Finished date must be in the past or present.")
	private LocalDate finishedAt;

	protected UserPieceUpdateRequest() {
	}

	public UserPieceUpdateRequest(Integer score, UserPieceStatus status, LocalDate startedAt, LocalDate finishedAt) {
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
