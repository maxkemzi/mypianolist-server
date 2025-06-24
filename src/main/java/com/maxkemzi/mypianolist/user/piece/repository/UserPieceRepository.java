package com.maxkemzi.mypianolist.user.piece.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maxkemzi.mypianolist.db.CrudRepository;
import com.maxkemzi.mypianolist.user.piece.model.UserPiece;
import com.maxkemzi.mypianolist.user.piece.model.UserPieceStatus;

public interface UserPieceRepository extends CrudRepository<UserPiece, UUID> {
	@Query("SELECT up FROM UserPiece up WHERE (:search IS NULL OR LOWER(up.piece.title) LIKE LOWER(CONCAT('%', :search, '%'))) AND (:status IS NULL OR up.status = :status) AND up.user.username = :username")
	Page<UserPiece> findByUsername(@Param("username") String username, @Param("search") String search,
			@Param("status") UserPieceStatus status,
			Pageable pageable);

	List<UserPiece> findByUserUsername(@Param("username") String username);

	boolean existsByUserUsernameAndPieceId(String username, UUID pieceId);

	Optional<UserPiece> findByUserUsernameAndPieceId(String username, UUID pieceId);

	void deleteByUserUsernameAndPieceId(String username, UUID pieceId);

	long countByPieceId(UUID pieceId);
}
