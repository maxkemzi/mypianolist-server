package com.maxkemzi.mypianolist.user.piece.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserPieceStatus {
	CURRENTLY_LEARNING,
	COMPLETED,
	DROPPED,
	PLAN_TO_LEARN,
	ON_HOLD;

	@JsonValue
	public String toLower() {
		return name().toLowerCase();
	}

	@JsonCreator
	public static UserPieceStatus fromString(String value) {
		return UserPieceStatus.valueOf(value.toUpperCase());
	}
}
