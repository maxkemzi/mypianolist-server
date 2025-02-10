package com.maxkemzi.mypianolist.piece.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maxkemzi.mypianolist.db.CrudRepository;
import com.maxkemzi.mypianolist.piece.model.Piece;

public interface PieceRepository extends CrudRepository<Piece, UUID> {
	Optional<Piece> findByTitleAndComposerId(String title, UUID id);

	@Query("SELECT p FROM Piece p WHERE (:genreName IS NULL OR p.genre.name = :genreName) AND (:search IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')))")
	Page<Piece> findAll(@Param("genreName") String genreName, @Param("search") String search, Pageable pageable);
}
