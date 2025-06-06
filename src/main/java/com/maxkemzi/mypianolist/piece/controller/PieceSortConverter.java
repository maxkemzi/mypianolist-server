package com.maxkemzi.mypianolist.piece.controller;

import org.springframework.core.convert.converter.Converter;

public class PieceSortConverter implements Converter<String, PieceSort> {
	@Override
	public PieceSort convert(String source) {
		switch (source) {
			case "created_at":
				return PieceSort.CREATED_AT;
			case "learners":
				return PieceSort.LEARNERS;
			case "favorites":
				return PieceSort.FAVORITES;
			default:
				throw new IllegalArgumentException("Invalid PieceSort value.");
		}
	}
}
