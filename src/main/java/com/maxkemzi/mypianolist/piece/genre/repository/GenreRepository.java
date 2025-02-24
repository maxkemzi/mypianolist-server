package com.maxkemzi.mypianolist.piece.genre.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.db.CrudRepository;
import com.maxkemzi.mypianolist.piece.genre.model.Genre;

public interface GenreRepository extends CrudRepository<Genre, UUID> {
	Optional<Genre> findByName(String name);

	Page<Genre> findAll(Pageable pageable);

	boolean existsByName(String name);
}
