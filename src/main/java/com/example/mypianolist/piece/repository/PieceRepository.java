package com.example.mypianolist.piece.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.mypianolist.util.CrudRepository;
import com.example.mypianolist.composer.model.Composer;
import com.example.mypianolist.piece.model.Piece;

public interface PieceRepository extends CrudRepository<Piece, UUID> {
	@Query("SELECT p FROM Piece p WHERE p.composer.id = :id")
	Iterable<Piece> findByComposerId(@Param("id") UUID id);

	@Query("SELECT p FROM Piece p WHERE p.genre.id = :id")
	Iterable<Piece> findByGenreId(@Param("id") UUID id);

	@Query("SELECT p FROM Piece p WHERE p.genre.id = :id AND LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%'))")
	Iterable<Piece> findByGenreIdAndSearchQuery(UUID id, String query);

	@Query("SELECT p FROM Piece p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%'))")
	Iterable<Piece> findBySearchQuery(String query);

	@Query("SELECT p FROM Piece p WHERE p.title = :title AND p.composer.id = :id")
	Optional<Piece> findByTitleAndComposerId(String title, UUID id);
}
