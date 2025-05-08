package com.maxkemzi.mypianolist.user.favouritepiece.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.user.favouritepiece.model.FavouritePiece;
import com.maxkemzi.mypianolist.db.CrudRepository;

public interface FavouritePieceRepository extends CrudRepository<FavouritePiece, UUID> {
	Page<FavouritePiece> findByUserUsername(String username, Pageable pageable);

	void deleteByUserUsernameAndPieceId(String username, UUID pieceId);

	boolean existsByUserUsernameAndPieceId(String username, UUID pieceId);

	long countByPieceId(UUID pieceId);
}
