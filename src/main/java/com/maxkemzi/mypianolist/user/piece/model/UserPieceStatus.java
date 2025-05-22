package com.maxkemzi.mypianolist.user.piece.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserPieceStatus {
	CURRENTLY_LEARNING,
	COMPLETED,
	DROPPED,
	PLAN_TO_LEARN;

	@JsonCreator
	public static UserPieceStatus fromString(String value) {
		try {
			return UserPieceStatus.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new InvalidUserPieceStatusException();
		}
	}

	@JsonValue
	public String getValue() {
		return name().toLowerCase();
	}
}
