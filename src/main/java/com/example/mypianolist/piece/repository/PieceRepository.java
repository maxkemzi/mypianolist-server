package com.example.mypianolist.piece.repository;

import java.util.UUID;

import com.example.mypianolist.CrudRepository;
import com.example.mypianolist.piece.model.Piece;

public interface PieceRepository extends CrudRepository<Piece, UUID> {
	Iterable<Piece> findByComposerId(UUID id);

	Iterable<Piece> findByGenreId(UUID id);
}
