package com.maxkemzi.mypianolist.piece.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PieceSort {
	CREATED_AT,
	LEARNERS,
	FAVORITES;

	@JsonCreator
	public static PieceSort fromString(String value) {
		try {
			return PieceSort.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new InvalidPieceSortException();
		}
	}

	@JsonValue
	public String getValue() {
		return name().toLowerCase();
	}

	public static class Constants {
		public static final String CREATED_AT = "CREATED_AT";
		public static final String LEARNERS = "LEARNERS";
		public static final String FAVORITES = "FAVORITES";
	}
}
