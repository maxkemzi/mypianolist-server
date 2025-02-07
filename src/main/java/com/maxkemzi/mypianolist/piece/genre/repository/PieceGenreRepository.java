package com.maxkemzi.mypianolist.piece.genre.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.piece.genre.model.PieceGenre;
import com.maxkemzi.mypianolist.util.CrudRepository;

public interface PieceGenreRepository extends CrudRepository<PieceGenre, UUID> {
	Optional<PieceGenre> findByName(String name);

	Page<PieceGenre> findAll(Pageable pageable);

	boolean existsByName(String name);
}
