package com.maxkemzi.mypianolist.user.controller;

import java.util.UUID;

import com.maxkemzi.mypianolist.piece.controller.PieceResponseDTO;
import com.maxkemzi.mypianolist.user.model.UserFavouritePiece;

public class UserFavouritePieceResponseDTO {
	private UUID id;
	private UserResponseDTO user;
	private PieceResponseDTO piece;

	protected UserFavouritePieceResponseDTO() {
	}

	public UserFavouritePieceResponseDTO(UserFavouritePiece ufp) {
		this.id = ufp.getId();
		this.user = new UserResponseDTO(ufp.getUser());
		this.piece = new PieceResponseDTO(ufp.getPiece());
	}

	public UUID getId() {
		return id;
	}

	public UserResponseDTO getUser() {
		return user;
	}

	public PieceResponseDTO getPiece() {
		return piece;
	}
}
