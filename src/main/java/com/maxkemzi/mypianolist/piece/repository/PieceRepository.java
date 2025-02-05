package com.maxkemzi.mypianolist.piece.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maxkemzi.mypianolist.util.CrudRepository;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.model.PieceGenre;

public interface PieceRepository extends CrudRepository<Piece, UUID> {
	Optional<Piece> findByTitleAndComposerId(@Param("title") String title, @Param("id") UUID id);

	@Query("SELECT p FROM Piece p WHERE (:genre IS NULL OR p.genre = :genre) AND (:search = '' OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')))")
	Page<Piece> findAll(@Param("genre") PieceGenre genre, @Param("search") String search, Pageable pageable);
}
