package com.maxkemzi.mypianolist.user.piece.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.user.piece.model.UserPiece;
import com.maxkemzi.mypianolist.db.CrudRepository;

public interface UserPieceRepository extends CrudRepository<UserPiece, UUID> {
	Page<UserPiece> findByUserUsername(String username, Pageable pageable);

	boolean existsByUserUsernameAndPieceId(String username, UUID pieceId);

	Optional<UserPiece> findByUserUsernameAndPieceId(String username, UUID pieceId);

	void deleteByUserUsernameAndPieceId(String username, UUID pieceId);

	long countByPieceId(UUID pieceId);
}
