package com.maxkemzi.mypianolist.user.favoritepiece.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.user.favoritepiece.model.FavoritePiece;
import com.maxkemzi.mypianolist.db.CrudRepository;

public interface FavoritePieceRepository extends CrudRepository<FavoritePiece, UUID> {
	Page<FavoritePiece> findByUserUsername(String username, Pageable pageable);

	void deleteByUserUsernameAndPieceId(String username, UUID pieceId);

	boolean existsByUserUsernameAndPieceId(String username, UUID pieceId);

	long countByPieceId(UUID pieceId);
}
