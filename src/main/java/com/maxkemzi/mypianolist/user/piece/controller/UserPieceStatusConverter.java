package com.maxkemzi.mypianolist.user.piece.controller;

import org.springframework.core.convert.converter.Converter;

import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

public class UserPieceStatusConverter implements Converter<String, UserPieceStatus> {
	@Override
	public UserPieceStatus convert(String source) {
		switch (source) {
			case "currently_learning":
				return UserPieceStatus.CURRENTLY_LEARNING;
			case "completed":
				return UserPieceStatus.COMPLETED;
			case "dropped":
				return UserPieceStatus.DROPPED;
			case "plan_to_learn":
				return UserPieceStatus.PLAN_TO_LEARN;
			default:
				throw new IllegalArgumentException("Invalid UserPieceStatus value.");
		}
	}
}
