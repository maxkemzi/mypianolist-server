package com.maxkemzi.mypianolist.user.controller;

import java.time.LocalDate;
import java.util.UUID;

import com.maxkemzi.mypianolist.piece.controller.PieceResponseDTO;
import com.maxkemzi.mypianolist.user.model.UserPiece;
import com.maxkemzi.mypianolist.user.model.UserPieceStatus;

public class UserPieceResponseDTO {
	private UUID id;
	private Integer score;
	private UserPieceStatus status;
	private LocalDate startedAt;
	private LocalDate finishedAt;
	private PieceResponseDTO piece;
	private UserResponseDTO user;

	protected UserPieceResponseDTO() {
	}

	public UserPieceResponseDTO(UserPiece userPiece) {
		this.id = userPiece.getId();
		this.score = userPiece.getScore();
		this.status = userPiece.getStatus();
		this.startedAt = userPiece.getStartedAt();
		this.finishedAt = userPiece.getFinishedAt();
		this.piece = new PieceResponseDTO(userPiece.getPiece());
		this.user = new UserResponseDTO(userPiece.getUser());
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

	public UserResponseDTO getUser() {
		return user;
	}
}
