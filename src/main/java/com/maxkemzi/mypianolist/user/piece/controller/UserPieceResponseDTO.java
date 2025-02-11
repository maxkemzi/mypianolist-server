package com.maxkemzi.mypianolist.user.piece.controller;

import java.time.LocalDate;
import java.util.UUID;

import com.maxkemzi.mypianolist.piece.controller.PieceResponseDTO;
import com.maxkemzi.mypianolist.user.piece.entity.UserPiece;
import com.maxkemzi.mypianolist.user.piece.entity.UserPieceStatus;

public class UserPieceResponseDTO {
	private UUID id;
	private Integer score;
	private UserPieceStatus status;
	private LocalDate startedAt;
	private LocalDate finishedAt;
	private PieceResponseDTO piece;

	protected UserPieceResponseDTO() {
	}

	public UserPieceResponseDTO(UserPiece userPiece) {
		this.id = userPiece.getId();
		this.score = userPiece.getScore();
		this.status = userPiece.getStatus();
		this.startedAt = userPiece.getStartedAt();
		this.finishedAt = userPiece.getFinishedAt();
		this.piece = new PieceResponseDTO(userPiece.getPiece());
	}

	public UUID getId() {
		return id;
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

	public PieceResponseDTO getPiece() {
		return piece;
	}
}
