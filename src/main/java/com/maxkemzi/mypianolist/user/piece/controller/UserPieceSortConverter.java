package com.maxkemzi.mypianolist.user.piece.controller;

import org.springframework.core.convert.converter.Converter;

public class UserPieceSortConverter implements Converter<String, UserPieceSort> {
	@Override
	public UserPieceSort convert(String source) {
		switch (source) {
			case "created_at":
				return UserPieceSort.CREATED_AT;
			case "score":
				return UserPieceSort.SCORE;
			default:
				throw new IllegalArgumentException("Invalid UserPieceSort value.");
		}
	}
}
