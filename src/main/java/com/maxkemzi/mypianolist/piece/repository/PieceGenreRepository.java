package com.maxkemzi.mypianolist.piece.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;

import com.maxkemzi.mypianolist.util.CrudRepository;
import com.maxkemzi.mypianolist.piece.model.PieceGenre;

public interface PieceGenreRepository extends CrudRepository<PieceGenre, UUID> {
	@Query("SELECT pg FROM PieceGenre pg WHERE pg.name = :name")
	Optional<PieceGenre> findByName(String name);
}
