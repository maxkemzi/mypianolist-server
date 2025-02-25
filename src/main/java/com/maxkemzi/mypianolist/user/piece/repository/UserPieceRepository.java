package com.maxkemzi.mypianolist.user.piece.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.user.piece.model.UserPiece;
import com.maxkemzi.mypianolist.db.CrudRepository;

public interface UserPieceRepository extends CrudRepository<UserPiece, UUID> {
	Page<UserPiece> findByUserUsername(String username, Pageable pageable);

	boolean existsByUserUsernameAndPieceId(String username, UUID id);

	Optional<UserPiece> findByUserUsernameAndId(String username, UUID id);

	boolean existsByUserUsernameAndId(String username, UUID id);
}
