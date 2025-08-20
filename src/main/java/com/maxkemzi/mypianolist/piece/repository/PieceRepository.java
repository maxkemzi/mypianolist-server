package com.maxkemzi.mypianolist.piece.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maxkemzi.mypianolist.db.CrudRepository;
import com.maxkemzi.mypianolist.piece.model.Piece;
import com.maxkemzi.mypianolist.piece.service.PieceWithStats;

public interface PieceRepository extends CrudRepository<Piece, UUID> {
	Optional<Piece> findByTitleAndComposerId(String title, UUID id);

	@Query("SELECT p FROM Piece p WHERE (:genreName IS NULL OR p.genre.name = :genreName) AND (:search IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')))")
	Page<Piece> findAll(@Param("genreName") String genreName, @Param("search") String search, Pageable pageable);

	@Query("""
			SELECT NEW com.maxkemzi.mypianolist.piece.service.PieceWithStats(p, COUNT(l), COUNT(f)) FROM Piece p
			LEFT JOIN UserPiece l ON l.piece.id = p.id
			LEFT JOIN FavoritePiece f ON f.piece.id = p.id
			WHERE (:genreName IS NULL OR p.genre.name = :genreName) AND (:search IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')))
			GROUP BY p
			""")
	Page<PieceWithStats> findAllWithStats(@Param("genreName") String genreName, @Param("search") String search,
			Pageable pageable);

	@Query("""
			SELECT NEW com.maxkemzi.mypianolist.piece.service.PieceWithStats(p, COUNT(l), COUNT(f)) FROM Piece p
			LEFT JOIN UserPiece l ON l.piece.id = p.id
			LEFT JOIN FavoritePiece f ON f.piece.id = p.id
			WHERE (:genreName IS NULL OR p.genre.name = :genreName) AND (:search IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')))
			GROUP BY p
			ORDER BY p.createdAt DESC
			""")
	Page<PieceWithStats> findAllWithStatsOrderByCreatedAt(@Param("genreName") String genreName,
			@Param("search") String search,
			Pageable pageable);

	@Query("""
			SELECT NEW com.maxkemzi.mypianolist.piece.service.PieceWithStats(p, COUNT(l), COUNT(f)) FROM Piece p
			LEFT JOIN UserPiece l ON l.piece.id = p.id
			LEFT JOIN FavoritePiece f ON f.piece.id = p.id
			WHERE (:genreName IS NULL OR p.genre.name = :genreName) AND (:search IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')))
			GROUP BY p
			ORDER BY COUNT(l) DESC
			""")
	Page<PieceWithStats> findAllWithStatsOrderByLearners(@Param("genreName") String genreName,
			@Param("search") String search,
			Pageable pageable);

	@Query("""
			SELECT NEW com.maxkemzi.mypianolist.piece.service.PieceWithStats(p, COUNT(l), COUNT(f)) FROM Piece p
			LEFT JOIN UserPiece l ON l.piece.id = p.id
			LEFT JOIN FavoritePiece f ON f.piece.id = p.id
			WHERE (:genreName IS NULL OR p.genre.name = :genreName) AND (:search IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')))
			GROUP BY p
			ORDER BY COUNT(f) DESC
			""")
	Page<PieceWithStats> findAllWithStatsOrderByFavorites(@Param("genreName") String genreName,
			@Param("search") String search,
			Pageable pageable);

	List<Piece> findAll();

	@Query("""
			SELECT NEW com.maxkemzi.mypianolist.piece.service.PieceWithStats(p, COUNT(l), COUNT(f)) FROM Piece p
			LEFT JOIN UserPiece l ON l.piece.id = p.id
			LEFT JOIN FavoritePiece f ON f.piece.id = p.id
			WHERE p.id = :id
			GROUP BY p
			""")
	Optional<PieceWithStats> findByIdWithStats(@Param("id") UUID id);

	boolean existsByTitleAndComposerId(String title, UUID id);
}
