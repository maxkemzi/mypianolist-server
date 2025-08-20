package com.maxkemzi.mypianolist.piece.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.maxkemzi.mypianolist.piece.service.CompletePiece;
import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompletePieceResponseDto extends PieceWithStatsResponseDto {
	private Boolean inFavorites;
	private UserPieceStatus status;

	protected CompletePieceResponseDto() {
	}

	public CompletePieceResponseDto(CompletePiece p) {
		super(p.getPieceWithStats());

		if (p.getUserMetadata() != null) {
			this.inFavorites = p.getUserMetadata().getInFavorites();
			this.status = p.getUserMetadata().getStatus();
		}
	}

	public Boolean getInFavorites() {
		return inFavorites;
	}

	public UserPieceStatus getStatus() {
		return status;
	}
}
