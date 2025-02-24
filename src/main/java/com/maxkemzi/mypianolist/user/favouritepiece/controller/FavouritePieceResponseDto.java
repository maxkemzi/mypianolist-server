package com.maxkemzi.mypianolist.user.favouritepiece.controller;

import java.util.UUID;

import com.maxkemzi.mypianolist.piece.controller.PieceResponseDto;
import com.maxkemzi.mypianolist.user.controller.UserResponseDto;
import com.maxkemzi.mypianolist.user.favouritepiece.model.FavouritePiece;

public class FavouritePieceResponseDto {
	private UUID id;
	private UserResponseDto user;
	private PieceResponseDto piece;

	protected FavouritePieceResponseDto() {
	}

	public FavouritePieceResponseDto(FavouritePiece fp) {
		this.id = fp.getId();
		this.user = new UserResponseDto(fp.getUser());
		this.piece = new PieceResponseDto(fp.getPiece());
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
