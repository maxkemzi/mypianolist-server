package com.maxkemzi.mypianolist.user.favouritepiece.controller;

import java.util.UUID;

import com.maxkemzi.mypianolist.piece.controller.PieceResponseDto;
import com.maxkemzi.mypianolist.user.controller.UserResponseDto;
import com.maxkemzi.mypianolist.user.favouritepiece.model.UserFavouritePiece;

public class UserFavouritePieceResponseDto {
	private UUID id;
	private UserResponseDto user;
	private PieceResponseDto piece;

	protected UserFavouritePieceResponseDto() {
	}

	public UserFavouritePieceResponseDto(UserFavouritePiece ufp) {
		this.id = ufp.getId();
		this.user = new UserResponseDto(ufp.getUser());
		this.piece = new PieceResponseDto(ufp.getPiece());
	}

	public UUID getId() {
		return id;
	}

	public UserResponseDto getUser() {
		return user;
	}

	public PieceResponseDto getPiece() {
		return piece;
	}
}
