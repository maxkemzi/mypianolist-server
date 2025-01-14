package com.example.mypianolist.piece;

import java.util.UUID;

import com.example.mypianolist.CrudRepository;

public interface PieceGenreRepository extends CrudRepository<PieceGenre, UUID> {
	PieceGenre findByName(String name);
}
