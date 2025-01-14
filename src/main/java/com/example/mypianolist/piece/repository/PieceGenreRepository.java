package com.example.mypianolist.piece.repository;

import java.util.UUID;

import com.example.mypianolist.CrudRepository;
import com.example.mypianolist.piece.model.PieceGenre;

public interface PieceGenreRepository extends CrudRepository<PieceGenre, UUID> {
	PieceGenre findByName(String name);
}
