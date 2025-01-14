package com.example.mypianolist.piece;

import java.util.UUID;

import com.example.mypianolist.CrudRepository;

public interface PieceRepository extends CrudRepository<Piece, UUID> {
	Iterable<Piece> findByComposerId(UUID id);

	Iterable<Piece> findByGenreId(UUID id);
}
