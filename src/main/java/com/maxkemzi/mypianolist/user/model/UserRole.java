package com.maxkemzi.mypianolist.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
	USER,
	ADMIN;

	@JsonCreator
	public static UserRole fromString(String value) {
		try {
			return UserRole.valueOf(value.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new InvalidUserRoleException();
		}
	}

	@JsonValue
	public String getValue() {
		return name().toLowerCase();
	}
}
