package com.maxkemzi.mypianolist.user.piece.controller;

import java.time.LocalDate;

import com.maxkemzi.mypianolist.piece.controller.PieceResponseDto;
import com.maxkemzi.mypianolist.user.piece.model.UserPiece;
import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

public class UserPieceResponseDto extends PieceResponseDto {
	private Integer score;
	private UserPieceStatus status;
	private LocalDate startedAt;
	private LocalDate finishedAt;

	protected UserPieceResponseDto() {
	}

	public UserPieceResponseDto(UserPiece up) {
		super(up.getPiece());
		this.score = up.getScore();
		this.status = up.getStatus();
		this.startedAt = up.getStartedAt();
		this.finishedAt = up.getFinishedAt();
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
