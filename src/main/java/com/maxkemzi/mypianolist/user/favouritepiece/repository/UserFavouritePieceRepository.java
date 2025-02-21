package com.maxkemzi.mypianolist.user.favouritepiece.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maxkemzi.mypianolist.user.favouritepiece.model.UserFavouritePiece;
import com.maxkemzi.mypianolist.db.CrudRepository;

public interface UserFavouritePieceRepository extends CrudRepository<UserFavouritePiece, UUID> {
	Page<UserFavouritePiece> findByUserUsername(String username, Pageable pageable);

	void deleteByUserUsernameAndPieceId(String username, UUID pieceId);

	boolean existsByUserUsernameAndPieceId(String username, UUID pieceId);
}
