package com.maxkemzi.mypianolist.user.piece.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserPieceSort {
	CREATED_AT,
	SCORE;

	@JsonCreator
	public static UserPieceSort fromString(String value) {
		try {
			return UserPieceSort.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new InvalidUserPieceSortException();
		}
	}

	@JsonValue
	public String getValue() {
		return name().toLowerCase();
	}

	public static class Constants {
		public static final String CREATED_AT = "CREATED_AT";
		public static final String SCORE = "SCORE";
	}
}
